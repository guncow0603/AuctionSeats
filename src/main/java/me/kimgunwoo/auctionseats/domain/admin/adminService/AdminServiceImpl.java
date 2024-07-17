package me.kimgunwoo.auctionseats.domain.admin.adminService;

import lombok.RequiredArgsConstructor;
import me.kimgunwoo.auctionseats.domain.admin.dto.ZoneInfo;
import me.kimgunwoo.auctionseats.domain.admin.dto.request.GradeCreateRequest;
import me.kimgunwoo.auctionseats.domain.admin.dto.request.PlaceCreateRequest;
import me.kimgunwoo.auctionseats.domain.admin.dto.request.ShowsCreateRequest;
import me.kimgunwoo.auctionseats.domain.admin.dto.request.ZoneGradeCreateRequest;
import me.kimgunwoo.auctionseats.domain.admin.dto.response.GradeCreateResponse;
import me.kimgunwoo.auctionseats.domain.admin.dto.response.PlaceCreateResponse;
import me.kimgunwoo.auctionseats.domain.admin.dto.response.ShowsCreateResponse;
import me.kimgunwoo.auctionseats.domain.admin.dto.response.ZoneGradeCreateResponse;
import me.kimgunwoo.auctionseats.domain.auction.dto.request.AuctionCreateRequest;
import me.kimgunwoo.auctionseats.domain.auction.service.AuctionService;
import me.kimgunwoo.auctionseats.domain.grade.entity.Grade;
import me.kimgunwoo.auctionseats.domain.grade.entity.ZoneGrade;
import me.kimgunwoo.auctionseats.domain.grade.service.GradeService;
import me.kimgunwoo.auctionseats.domain.grade.service.ZoneGradeService;
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

    private final GradeService gradeService;

    private final ZoneGradeService zoneGradeService;

    private final AuctionService auctionService;

    public static final String S3_PATH = "https://auction-ticket.s3.ap-northeast-2.amazonaws.com/";
    public static final String FILE_PATH = "shows/";
    public static final String THUMBNAIL = "thumbnail/";
    public static final String GENERAL = "general/";


    // 공연장 및 구역 생성
    @Override
    @Transactional
    public List<PlaceCreateResponse> createPlaceAndZone(PlaceCreateRequest placesRequest) {
        List<ZoneInfo> zoneInfos = placesRequest.zoneInfos();
        Places places = placeService.createPlace(placesRequest);
        List<Zone> zoneList = zoneService.createZone(zoneInfos);
        places.updateZone(zoneList);

        return createPlaceResponse(zoneList);

    }

    // 공연장 및 구역 응답 생성
    @Override
    public List<PlaceCreateResponse> createPlaceResponse(List<Zone> zoneList) {
        List<PlaceCreateResponse> placesResponseList = new ArrayList<>();

        for (Zone zone : zoneList) {
            placesResponseList.add(new PlaceCreateResponse(zone.getName(), zone.getSeatNumber(), zone.getPlaces().getId()));
        }

        return placesResponseList;
    }

    //  공연과 관련된 공연 정보, 공연 카테고리, 공연 이미지, 공연 및 회차 생성
    @Override
    @Transactional
    public ShowsCreateResponse createShowsBundleAndSchedule(
            Long placeId,
            ShowsCreateRequest showsRequest,
            List<MultipartFile> multipartFiles) {

        Places places = placeService.getReferenceById(placeId);

        ShowsInfo showsInfo = showsInfoService.createShowsInfo(showsRequest);

        List<ShowsImage> showsImages = showsInfoService.createShowsImage(multipartFiles, showsInfo);
        showsInfo.addShowsImage(showsImages);

        ShowsCategory showsCategory = showsInfoService.createShowsCategory(showsRequest.categoryName());
        showsInfo.updateShowsCategory(showsCategory);

        Shows shows = showsService.createShows(showsRequest, places, showsInfo);

        showsInfo.addShows(shows);

        LocalTime startTime = showsRequest.startTime();
        scheduleService.createSchedule(shows, startTime);

        return new ShowsCreateResponse(shows.getId());

    }

    // 구역 생성
    @Override
    @Transactional
    public GradeCreateResponse createGrade(Long showsId, GradeCreateRequest gradeRequest) {
        Shows shows = showsService.findById(showsId);
        Grade grade = gradeService.createGrade(gradeRequest, shows);
        return new GradeCreateResponse(shows.getPlaces().getId(),grade.getId());
    }

    // 구역 등급 생성
    @Override
    @Transactional
    public ZoneGradeCreateResponse createZoneGrade(ZoneGradeCreateRequest zoneGradeRequest) {
        Zone zone = zoneService.getReferenceById(zoneGradeRequest.getZoneId());
        Grade grade = gradeService.getReferenceById(zoneGradeRequest.getGradeId());

        ZoneGrade zoneGrade = zoneGradeService.createZoneGrade(zoneGradeRequest, zone, grade);

        return new ZoneGradeCreateResponse(zoneGrade);
    }

    // 경매 생성
    @Override
    @Transactional
    public void createAuction(Long scheduleId, Long zoneGradeId, AuctionCreateRequest auctionCreateRequest) {
        auctionService.createAuction(scheduleId, zoneGradeId, auctionCreateRequest);
    }
}