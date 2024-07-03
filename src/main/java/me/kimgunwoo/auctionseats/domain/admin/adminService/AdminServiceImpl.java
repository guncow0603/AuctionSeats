package me.kimgunwoo.auctionseats.domain.admin.adminService;

import lombok.RequiredArgsConstructor;
import me.kimgunwoo.auctionseats.domain.admin.dto.request.PlacesRequest;
import me.kimgunwoo.auctionseats.domain.admin.dto.request.ShowsRequest;
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
        List<SeatRequest> seats = placesRequest.seats();
        Integer totalSeat = totalCountSeat(seats);

        Places places = placesRequest.toEntity(totalSeat);
        Places savePlaces = placeService.savePlace(places);

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

        Shows shows = showsRequest.toEntity(places);
        Shows saveShows = showsService.saveShows(shows);

        List<String> fileUrl = s3tUpload(files, saveShows.getId());
        List<ShowsImage> showsImageList = saveAllShowsImage(fileUrl, saveShows);

        saveShows.createShowsImage(showsImageList);

        ShowsCategory showsCategory = createShowsCategory(showsRequest.categoryName());
        saveShows.createShowsCategory(showsCategory);

        createSequence(saveShows, showsRequest.startTime());
    }

    // 총 좌석 개수 연산
    private Integer totalCountSeat(List<SeatRequest> seatRequests) {
        Integer totalSeat = 0;

        for (SeatRequest seat : seatRequests) {
            totalSeat += seat.getZoneCountSeat();
        }

        return totalSeat;
    }

    // 좌석 생성
    private List<Seat> createSeat(List<SeatRequest> seats, Places places) {
        List<Seat> seatList = new ArrayList<>();

        seatList = seats.stream()
                .flatMap(seat -> IntStream.rangeClosed(1, seat.getZoneCountSeat())
                        .mapToObj(i -> seat.toEntity(places, i)))
                .collect(Collectors.toList());

        return seatList;

    }

    // 이미지 저장
    public List<ShowsImage> saveAllShowsImage(List<String> fileKeyList, Shows shows) {
        List<ShowsImage> showsImageList = new ArrayList<>();
        for (String fileKey : fileKeyList) {
            if (fileKey.contains(THUMBNAIL)) {
                ShowsImage showsImage =
                        ShowsImage
                                .builder()
                                .s3Key(fileKey)
                                .type("대표")
                                .shows(shows)
                                .build();
                showsImageList.add(showsImage);
            } else if (fileKey.contains(GENERAL)) {
                ShowsImage showsImage =
                        ShowsImage
                                .builder()
                                .s3Key(fileKey)
                                .type("일반")
                                .shows(shows)
                                .build();
                showsImageList.add(showsImage);
            }
        }
        return showsService.saveAllShowsImage(showsImageList);
    }

    // S3 저장
    public List<String> s3tUpload(List<MultipartFile> fileList, Long showId) {
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
    public void createSequence(Shows shows, LocalTime startTime) {
        List<Sequence> sequenceList = new ArrayList<>();
        LocalDate startDate = shows.getStartDate();
        LocalDate endDate = shows.getEndDate();
        long daysBetween = ChronoUnit.DAYS.between(startDate, endDate);
        for (int i = 1; i <= daysBetween; i++) {
            LocalDateTime dateTIme = startDate.atTime(startTime);
            Sequence sequence =
                    Sequence
                            .builder()
                            .startDateTime(dateTIme)
                            .shows(shows)
                            .sequence(i)
                            .build();
            sequenceList.add(sequence);
            startDate = startDate.plusDays(1);
        }

        sequenceService.saveAllSequence(sequenceList);
    }

    // 카테고리 생성 기타 입력시
    public ShowsCategory createShowsCategory(String category) {
        ShowsCategory showsCategory = showsSequenceSeatService.findShowsCategory(category);
        if (showsCategory == null) {
            showsCategory =
                    ShowsCategory
                            .builder()
                            .name(category)
                            .build();
        }

        return showsSequenceSeatService.saveShowSCategory(showsCategory);
    }

}
