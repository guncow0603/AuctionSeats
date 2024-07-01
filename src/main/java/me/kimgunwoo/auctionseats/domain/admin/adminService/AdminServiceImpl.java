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
import me.kimgunwoo.auctionseats.domain.sequence.service.SequenceServiceImpl;
import me.kimgunwoo.auctionseats.domain.show.entity.ImageType;
import me.kimgunwoo.auctionseats.domain.show.entity.Shows;
import me.kimgunwoo.auctionseats.domain.show.entity.ShowsImage;
import me.kimgunwoo.auctionseats.domain.show.service.ShowsServiceImpl;
import me.kimgunwoo.auctionseats.domain.shows_sequence_seat.service.ShowsSequenceSeatServiceImpl;
import me.kimgunwoo.auctionseats.global.util.S3Uploader;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

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

    // 공연 생성
    @Override
    @Transactional
    public void createShows(ShowsRequest showsRequest, Long placeId, List<MultipartFile> files) {
        Places places = placeService.findPlace(placeId);

        Shows shows = showsRequest.toEntity(places);
        Shows saveShows = showsService.saveShows(shows);

        List<String> fileUrl = s3tUpload(files, saveShows.getId());
        List<ShowsImage> showsImageList = saveAllShowsImage(fileUrl, saveShows);

        saveShows.createShowsImage(showsImageList);

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
                                .type(String.valueOf(ImageType.POSTER_IMG))
                                .shows(shows)
                                .build();
                showsImageList.add(showsImage);
            } else if (fileKey.contains(GENERAL)) {
                ShowsImage showsImage =
                        ShowsImage
                                .builder()
                                .s3Key(fileKey)
                                .type(String.valueOf(ImageType.INFO_IMG))
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

        for (SeatRequest seat : seats) {
            for (int i = 1; i <= seat.getZoneCountSeat(); i++) {
                seatList.add(seat.toEntity(places, i));
            }
        }

        return seatList;
    }

}
