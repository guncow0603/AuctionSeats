package me.kimgunwoo.auctionseats.domain.admin.adminService;

import lombok.RequiredArgsConstructor;
import me.kimgunwoo.auctionseats.domain.admin.dto.request.PlacesRequest;
import me.kimgunwoo.auctionseats.domain.admin.dto.request.ShowsRequest;
import me.kimgunwoo.auctionseats.domain.admin.dto.request.ShowsSequenceSeatRequest;
import me.kimgunwoo.auctionseats.domain.admin.dto.response.PlacesResponse;
import me.kimgunwoo.auctionseats.domain.place.entity.Places;
import me.kimgunwoo.auctionseats.domain.place.service.PlaceServiceImpl;
import me.kimgunwoo.auctionseats.domain.seat.dto.request.SeatRequest;
import me.kimgunwoo.auctionseats.domain.seat.entity.Seat;
import me.kimgunwoo.auctionseats.domain.seat.service.SeatServiceImpl;
import me.kimgunwoo.auctionseats.domain.sequence.entity.Sequence;
import me.kimgunwoo.auctionseats.domain.sequence.service.SequenceServiceImpl;
import me.kimgunwoo.auctionseats.domain.show.entity.Shows;
import me.kimgunwoo.auctionseats.domain.show.entity.ShowsCategory;
import me.kimgunwoo.auctionseats.domain.show.entity.ShowsImage;
import me.kimgunwoo.auctionseats.domain.show.service.ShowsServiceImpl;
import me.kimgunwoo.auctionseats.domain.shows_sequence_seat.entity.ShowsSequenceSeat;
import me.kimgunwoo.auctionseats.domain.shows_sequence_seat.service.ShowsSequenceSeatServiceImpl;
import me.kimgunwoo.auctionseats.global.util.S3Uploader;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final ShowsServiceImpl showsService;

    private final PlaceServiceImpl placeService;

    private final SequenceServiceImpl sequenceService;

    private final SeatServiceImpl seatService;

    private final ShowsSequenceSeatServiceImpl showsSequenceSeatService;

    private final S3Uploader s3Uploader;

    public static final String S3_PATH = "https://auction-ticket.s3.ap-northeast-2.amazonaws.com/";

    public static final String FILE_PATH = "shows/"; // shows/  thumbnail/ + (String)showsId

    public static final String THUMBNAIL = "thumbnail/";

    public static final String GENERAL = "general/";

    // 공연장 생성
    @Override
    @Transactional
    public List<PlacesResponse> createPlace(PlacesRequest placesRequest) {
        // 좌석 카운트
        List<SeatRequest> seats = placesRequest.seats();
        Integer totalSeat = totalCountSeat(seats);
        // 공연 저장
        Places places = placesRequest.toEntity(totalSeat);
        Places savePlaces = placeService.savePlace(places);
        // 좌석 저장
        List<Seat> seatList = createSeat(seats, savePlaces);
        seatService.saveAllSeat(seatList);

        List<PlacesResponse> placeResponseList = new ArrayList<>();
        for (SeatRequest seat : seats) {
            placeResponseList.add(new PlacesResponse(seat.getZone(), seat.getZoneCountSeat(), places));
        }
        return placeResponseList;
    }

    // 공연 및 회차 생성
    @Override
    @Transactional
    public void createShowsAndSequence(ShowsRequest showsRequest, Long placeId, List<MultipartFile> files) {
        Places places = placeService.findPlace(placeId);
        // 공연 저장
        Shows shows = showsRequest.toEntity(places);
        Shows saveShows = showsService.saveShows(shows);

        // 이미지 저장
        List<String> fileUrl = s3tUpload(files, saveShows.getId());
        List<ShowsImage> showsImageList = saveAllShowsImage(fileUrl, saveShows);

        saveShows.addShowsImage(showsImageList);

        // 카테고리 저장
        ShowsCategory showsCategory = createShowsCategory(showsRequest.categoryName());
        saveShows.updateShowsCategory(showsCategory);

        // 회차 저장
        createSequence(saveShows, showsRequest.startTime());
    }

    // 공연별 회차 생성 및 경매 생성
    @Override
    @Transactional
    public void createShowsSequenceSeatAndAuction(
            Long placeId,
            Long sequenceId,
            ShowsSequenceSeatRequest showsSequenceSeatRequest
    ) {
        List<Seat> allSeatOfZone = seatService.findAllSeatOfZone(placeId, showsSequenceSeatRequest.getZone());
        Sequence sequence = sequenceService.findSequence(sequenceId);
        List<ShowsSequenceSeat> showsSequenceSeatList = checkAndCreateAuctionSeat(allSeatOfZone, sequence,
                showsSequenceSeatRequest);

        saveAllShowsSequenceSeat(showsSequenceSeatList);

        List<ShowsSequenceSeat> sequenceAuctionList = showsSequenceSeatService.findAllBySequenceId(sequence.getId());
        // createAuction(List<ShowsSequenceSeat> sequenceAuctionList);
    }

    // 옥션 공연 회차별 좌석 생성
    public List<ShowsSequenceSeat> checkAndCreateAuctionSeat(
            List<Seat> allSeatOfZone,
            Sequence sequence,
            ShowsSequenceSeatRequest showsSequenceSeatRequest
    ) {
        List<Integer> auctionSeatList = showsSequenceSeatRequest.auctionSeats();
        List<ShowsSequenceSeat> showsSequenceSeatList = new ArrayList<>();

        for (Integer seatNumber : auctionSeatList) {
            // 임시 체크 처리
            if (Objects.equals(allSeatOfZone.get(seatNumber).getSeatNumber(), seatNumber)) {
                Seat auctionSeat = allSeatOfZone.get(seatNumber);
                ShowsSequenceSeat auctionShowsSequenceSeat =
                        showsSequenceSeatRequest.auctionToEntity(
                                auctionSeat,
                                sequence
                        );
                showsSequenceSeatList.add(auctionShowsSequenceSeat);
                allSeatOfZone.remove(seatNumber.intValue());

            }
        }

        return restCreateGeneralSeat(showsSequenceSeatList, allSeatOfZone, sequence, showsSequenceSeatRequest);
    }

    // 일반 좌석 공연 회차별 좌석 생성
    public List<ShowsSequenceSeat> restCreateGeneralSeat(
            List<ShowsSequenceSeat> showsSequenceSeatList,
            List<Seat> allSeatOfZone,
            Sequence sequence,
            ShowsSequenceSeatRequest showsSequenceSeatRequest
    ) {

        for (Seat seat : allSeatOfZone) {
            showsSequenceSeatList.add(showsSequenceSeatRequest.generalToEntity(seat, sequence));
        }

        return showsSequenceSeatList;
    }

    // 모든 좌석 생성
    public void saveAllShowsSequenceSeat(List<ShowsSequenceSeat> showsSequenceSeatList) {
        showsSequenceSeatService.saveAllShowsSequenceSeat(showsSequenceSeatList);
    }

    // 총 좌석 개수 연산
    public Integer totalCountSeat(List<SeatRequest> seatRequests) {
        Integer totalSeat = 0;

        for (SeatRequest seat : seatRequests) {
            totalSeat += seat.getZoneCountSeat();
        }

        return totalSeat;
    }

    // 좌석 생성
    private List<Seat> createSeat(List<SeatRequest> seats, Places places) {
        return seats.stream()
                .flatMap(seat -> IntStream.rangeClosed(1, seat.getZoneCountSeat())
                        .mapToObj(i -> seat.toEntity(places, i)))
                .collect(Collectors.toList());


    }

    // 이미지 저장
    private List<ShowsImage> saveAllShowsImage(List<String> fileKeyList, Shows shows) {
        List<ShowsImage> showsImageList = divideShowsImageList(fileKeyList, shows);
        return showsService.saveAllShowsImage(showsImageList);
    }

    // 이미지 종류 분리
    private List<ShowsImage> divideShowsImageList(List<String> fileKeyList, Shows shows) {
        List<ShowsImage> returnShowsIamgeList = new ArrayList<>();
        for (String fileKey : fileKeyList) {
            ShowsImage showsImage =
                    ShowsImage
                            .builder()
                            .s3Key(fileKey)
                            .type(this.checkShowsType(fileKey))
                            .shows(shows)
                            .build();
            returnShowsIamgeList.add(showsImage);
        }
        return returnShowsIamgeList;
    }

    // 이미지 종류 체크
    private String checkShowsType(String type) {
        if (type.contains(THUMBNAIL)) {
            return "대표";
        }
        return "일반";
    }

    // S3 저장
    private List<String> s3tUpload(List<MultipartFile> fileList, Long showId) {
        List<String> fileUrl = new ArrayList<>();

        String thumbnailFilePath = FILE_PATH + THUMBNAIL + showId;
        String generalFilePath = FILE_PATH + GENERAL + showId;

        MultipartFile thumbnailMultipartFile = fileList.get(0);
        fileList.remove(0);

        String thumbnailUrl = s3Uploader.uploadSingleFileToS3(thumbnailMultipartFile, thumbnailFilePath);
        fileUrl.add(thumbnailUrl);

        List<String> generalUrl = s3Uploader.uploadFileToS3(fileList, generalFilePath);
        fileUrl.addAll(generalUrl);

        return fileUrl;
    }

    // 회차 생성
    private void createSequence(Shows shows, LocalTime startTime) {
        List<Sequence> saveSequenceList = distributeSequence(shows, startTime);
        saveSequence(saveSequenceList);
    }

    private void saveSequence(List<Sequence> sequenceList) {
        sequenceService.saveAllSequence(sequenceList);
    }

    // 회차 분리
    private List<Sequence> distributeSequence(Shows shows, LocalTime startTime) {
        LocalDate startDate = shows.getStartDate();
        LocalDate endDate = shows.getEndDate();
        List<Sequence> distributeSequenceList = new ArrayList<>();
        long daysBetween = ChronoUnit.DAYS.between(startDate, endDate);
        for (int i = 1; i <= daysBetween; i++) {
            LocalDateTime dateTime = startDate.atTime(startTime);
            Sequence sequence =
                    Sequence
                            .builder()
                            .startDateTime(dateTime)
                            .shows(shows)
                            .sequence(i)
                            .build();
            distributeSequenceList.add(sequence);
            startDate = startDate.plusDays(1);
        }
        return distributeSequenceList;
    }

    // 카테고리 생성 기타 입력시
    private ShowsCategory createShowsCategory(String category) {
        ShowsCategory showsCategory = showsService.findShowsCategory(category);
        if (showsCategory == null) {
            showsCategory =
                    ShowsCategory
                            .builder()
                            .name(category)
                            .build();
        }
        return showsService.saveShowsCategory(showsCategory);
    }

}
