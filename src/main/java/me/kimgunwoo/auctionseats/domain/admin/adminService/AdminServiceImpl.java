package me.kimgunwoo.auctionseats.domain.admin.adminService;

import lombok.RequiredArgsConstructor;
import me.kimgunwoo.auctionseats.domain.admin.dto.ZoneInfo;
import me.kimgunwoo.auctionseats.domain.admin.dto.request.GradeRequest;
import me.kimgunwoo.auctionseats.domain.admin.dto.request.PlacesRequest;
import me.kimgunwoo.auctionseats.domain.admin.dto.request.ShowsRequest;
import me.kimgunwoo.auctionseats.domain.admin.dto.response.GradeResponse;
import me.kimgunwoo.auctionseats.domain.admin.dto.response.PlacesResponse;
import me.kimgunwoo.auctionseats.domain.admin.dto.response.ShowsResponse;
import me.kimgunwoo.auctionseats.domain.grade.entity.Grade;
import me.kimgunwoo.auctionseats.domain.grade.service.GradeService;
import me.kimgunwoo.auctionseats.domain.place.entity.Places;
import me.kimgunwoo.auctionseats.domain.place.entity.Zone;
import me.kimgunwoo.auctionseats.domain.place.service.PlaceService;
import me.kimgunwoo.auctionseats.domain.place.service.ZoneService;
import me.kimgunwoo.auctionseats.domain.schedule.service.ScheduleService;
import me.kimgunwoo.auctionseats.domain.show.entity.Shows;
import me.kimgunwoo.auctionseats.domain.show.entity.ShowsCategory;
import me.kimgunwoo.auctionseats.domain.show.entity.ShowsImage;
import me.kimgunwoo.auctionseats.domain.show.entity.ShowsInfo;
import me.kimgunwoo.auctionseats.domain.show.service.ShowsInfoService;
import me.kimgunwoo.auctionseats.domain.show.service.ShowsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final PlaceService placeService;

    private final ZoneService zoneService;

    private final ShowsService showsService;

    private final ShowsInfoService showsInfoService;

    private final ScheduleService scheduleService;
    public static final String S3_PATH = "https://auction-ticket.s3.ap-northeast-2.amazonaws.com/";
    public static final String FILE_PATH = "shows/";
    public static final String THUMBNAIL = "thumbnail/";
    public static final String GENERAL = "general/";

    private final GradeService gradeService;

    // 공연장 및 구역 생성
    @Override
    @Transactional
    public List<PlacesResponse> createPlaceAndZone(PlacesRequest placesRequest) {
        List<ZoneInfo> zoneInfos = placesRequest.zoneInfos();
        Places places = placeService.createPlace(placesRequest);
        List<Zone> zoneList = zoneService.createZone(places, zoneInfos);
        places.updateZone(zoneList);

        return createPlaceResponse(zoneList);

    }

    // 공연장 및 구역 응답 생성
    @Override
    public List<PlacesResponse> createPlaceResponse(List<Zone> zoneList) {
        List<PlacesResponse> placesResponseList = new ArrayList<>();

        for (Zone zone : zoneList) {
            placesResponseList.add(new PlacesResponse(zone.getName(), zone.getSeatNumber(), zone.getPlaces().getId()));
        }

        return placesResponseList;
    }

    //  공연과 관련된 공연 정보, 공연 카테고리, 공연 이미지, 공연 및 회차 생성
    @Override
    @Transactional
    public ShowsResponse createShowsBundleAndSchedule(
            Long placeId,
            ShowsRequest showsRequest,
            List<MultipartFile> multipartFiles) {

        Places places = placeService.getReferenceById(placeId);

        ShowsInfo showsInfo = showsInfoService.createShowsInfo(showsRequest);

        List<ShowsImage> showsImages = showsInfoService.createShowsImage(multipartFiles, showsInfo);
        showsInfo.addShowsImage(showsImages);

        ShowsCategory showsCategory = showsInfoService.createShowsCategory(showsRequest.categoryName());
        showsInfo.updateShowsCategory(showsCategory);

        Shows shows = showsService.createShows(showsRequest, places, showsInfo);

        LocalTime startTime = showsRequest.startTime();
        scheduleService.createSchedule(shows, startTime);

        return new ShowsResponse(shows.getId());

    }

    // 구역 생성
    @Override
    @Transactional
    public GradeResponse createGrade(Long showsId, GradeRequest gradeRequest) {
        Shows shows = showsService.findById(showsId);
        Grade grade = gradeService.createGrade(gradeRequest, shows);
        return new GradeResponse(shows.getPlaces().getId(),grade.getId());
    }
}