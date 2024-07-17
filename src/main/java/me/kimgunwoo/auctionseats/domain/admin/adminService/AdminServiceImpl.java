package me.kimgunwoo.auctionseats.domain.admin.adminService;

import lombok.RequiredArgsConstructor;
import me.kimgunwoo.auctionseats.domain.admin.dto.ZoneInfo;
import me.kimgunwoo.auctionseats.domain.admin.dto.request.*;
import me.kimgunwoo.auctionseats.domain.admin.dto.response.*;
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
    private final ShowsService showsService;
    private final ZoneService zoneService;
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
    public List<PlaceCreateResponse> createPlaceAndZone(PlaceCreateRequest placeCreateRequest) {
        List<ZoneInfo> zoneInfos = placeCreateRequest.zoneInfos();
        Places places = placeService.createPlace(placeCreateRequest);
        List<Zone> zoneList = zoneService.createZone(zoneInfos);
        places.updateZone(zoneList);

        return createPlaceResponse(zoneList);
    }

    // 공연장 및 구역 응답 생성
    @Override
    public List<PlaceCreateResponse> createPlaceResponse(List<Zone> zoneList) {
        List<PlaceCreateResponse> placeCreateResponseList = new ArrayList<>();

        for (Zone zone : zoneList) {
            placeCreateResponseList.add(
                    new PlaceCreateResponse(zone.getName(), zone.getSeatNumber(), zone.getPlaces().getId()));
        }

        return placeCreateResponseList;
    }

    //  공연과 관련된 공연 정보, 공연 카테고리, 공연 이미지, 공연 및 회차 생성
    @Override
    @Transactional
    public ShowsInfoCreateResponse createShowsBundle(
            ShowsInfoCreateRequest showsInfoCreateRequest,
            List<MultipartFile> multipartFiles) {

        ShowsInfo showsInfo = showsInfoService.createShowsInfo(showsInfoCreateRequest);

        List<ShowsImage> showsImages = showsInfoService.createShowsImage(multipartFiles, showsInfo);
        showsInfo.addShowsImage(showsImages);

        ShowsCategory showsCategory = showsInfoService.createShowsCategory(showsInfoCreateRequest.getCategoryName());
        showsInfo.updateShowsCategory(showsCategory);

        return new ShowsInfoCreateResponse(showsInfo.getId());

    }

    // 공연 및 회차 생성
    @Override
    @Transactional
    public ShowsCreateResponse createShowsAndSchedule(
            ShowsCreateRequest showsCreateRequest,
            Long showsInfoId,
            Long placeId) {

        Places places = placeService.getReferenceById(placeId);

        ShowsInfo showsInfo = showsInfoService.findByShowsInfoId(showsInfoId);

        Shows shows = showsService.createShows(showsCreateRequest, places, showsInfo);
        showsInfo.addShows(shows);

        LocalTime startTime = showsCreateRequest.startTime();

        scheduleService.createSchedule(shows, startTime);

        return new ShowsCreateResponse(shows.getId());
    }

    // 구역 생성
    @Override
    @Transactional
    public GradeCreateResponse createGrade(Long showsId, GradeCreateRequest gradeCreateRequest) {
        Shows shows = showsService.findById(showsId);

        Grade grade = gradeService.createGrade(gradeCreateRequest, shows);

        return new GradeCreateResponse(shows.getPlaces().getId(), grade.getId());
    }

    // 구역 등급 생성
    @Override
    @Transactional
    public ZoneGradeCreateResponse createZoneGrade(ZoneGradeCreateRequest zoneGradeCreateRequest) {
        Zone zone = zoneService.getReferenceById(zoneGradeCreateRequest.getZoneId());
        Grade grade = gradeService.getReferenceById(zoneGradeCreateRequest.getGradeId());

        ZoneGrade zoneGrade = zoneGradeService.createZoneGrade(zoneGradeCreateRequest, zone, grade);

        return new ZoneGradeCreateResponse(zoneGrade);
    }

    // 경매 생성
    @Override
    @Transactional
    public void createAuction(Long scheduleId, Long zoneGradeId, AuctionCreateRequest auctionCreateRequest) {
        auctionService.createAuction(scheduleId, zoneGradeId, auctionCreateRequest);
    }
}