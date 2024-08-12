package me.kimgunwoo.auctionseats.domain.admin.service;


import me.kimgunwoo.auctionseats.domain.place.dto.ZoneInfo;
import me.kimgunwoo.auctionseats.domain.admin.dto.request.*;
import me.kimgunwoo.auctionseats.domain.admin.dto.response.PlaceCreateResponse;
import me.kimgunwoo.auctionseats.domain.admin.dto.response.ZoneGradeCreateResponse;
import me.kimgunwoo.auctionseats.domain.grade.entity.Grade;
import me.kimgunwoo.auctionseats.domain.grade.entity.ZoneGrade;
import me.kimgunwoo.auctionseats.domain.grade.service.GradeServiceImpl;
import me.kimgunwoo.auctionseats.domain.grade.service.ZoneGradeServiceImpl;
import me.kimgunwoo.auctionseats.domain.place.entity.Place;
import me.kimgunwoo.auctionseats.domain.place.entity.Zone;
import me.kimgunwoo.auctionseats.domain.place.service.PlaceServiceImpl;
import me.kimgunwoo.auctionseats.domain.place.service.ZoneServiceImpl;
import me.kimgunwoo.auctionseats.domain.show.entity.Shows;
import me.kimgunwoo.auctionseats.domain.show.entity.ShowsCategory;
import me.kimgunwoo.auctionseats.domain.show.entity.ShowsImage;
import me.kimgunwoo.auctionseats.domain.show.entity.ShowsInfo;
import me.kimgunwoo.auctionseats.domain.show.service.ShowsServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AdminServiceTest {

    @InjectMocks
    AdminServiceImpl adminService;

    @Mock
    PlaceServiceImpl placeService;

    @Mock
    ZoneServiceImpl zoneService;

    @Mock
    ShowsServiceImpl showsService;

    @Mock
    GradeServiceImpl gradeService;

    @Mock
    ZoneGradeServiceImpl zoneGradeService;

    public static PlaceCreateRequest placeCreateRequest;

    public static ShowsInfoCreateRequest showsInfoCreateRequest;

    public static GradeCreateRequest gradeCreateRequest;

    public static ZoneGradeCreateRequest zoneGradeCreateRequest;

    public static ShowsCreateRequest showsCreateRequest;

    @BeforeEach
    public void initPlaceRequest() {
        List<ZoneInfo> zoneInfos = new ArrayList<>();
        zoneInfos.add(new ZoneInfo("A", 100));
        zoneInfos.add(new ZoneInfo("B", 100));
        placeCreateRequest = new PlaceCreateRequest("공연장", "Address", zoneInfos);
        showsInfoCreateRequest = new ShowsInfoCreateRequest(
                "힙합 공연",
                "힙합 쇼케이스",
                12,
                360,
                "공연"
        );
        showsCreateRequest = new ShowsCreateRequest(
                "힙합 공연-서울",
                LocalDate.of(2023, 3, 1),
                LocalDate.of(2023, 3, 2),
                LocalTime.of(15, 0));
        gradeCreateRequest = new GradeCreateRequest("VIP", 100000L, 70000L);
        zoneGradeCreateRequest = new ZoneGradeCreateRequest(1L, 1L);
    }

    @Test
    void 공연장_생성_테스트() {
        // given
        Place place = placeCreateRequest.toEntity(100);
        List<Zone> zoneList = new ArrayList<>();
        zoneList.add(
                Zone
                        .builder()
                        .name(
                                placeCreateRequest.zoneInfos().get(0).zone())
                        .seatNumber(
                                placeCreateRequest.zoneInfos().get(0).seatNumber())
                        .build()
        );
        zoneList.add(Zone
                .builder()
                .name(
                        placeCreateRequest.zoneInfos().get(1).zone())
                .seatNumber(
                        placeCreateRequest.zoneInfos().get(1).seatNumber())
                .build()
        );
        place.updateZone(zoneList);

        //when
        given(placeService.createPlace(any())).willReturn(place);
        given(zoneService.createZone(any())).willReturn(zoneList);
        List<PlaceCreateResponse> response = adminService.createPlaceAndZone(placeCreateRequest);

        //then
        assertEquals("공연장", place.getName());
        assertEquals("Address", place.getAddress());
        assertEquals(response.get(0).zone(), zoneList.get(0).getName());
        assertEquals(response.get(0).zoneCountSeat(), zoneList.get(0).getSeatNumber());
        assertEquals(response.get(1).zone(), zoneList.get(1).getName());
        assertEquals(response.get(1).zoneCountSeat(), zoneList.get(1).getSeatNumber());

    }

    @Test
    void 공연정보_공연이미지_공연카테고리() {
        // given
        ShowsInfo showsInfo = showsInfoCreateRequest.toEntity();

        List<String> fileUrl = new ArrayList<>();
        fileUrl.add("shows/thumbnail/1/51579925-f563-4c75-9999-e2264dadbdab");
        fileUrl.add("shows/general/1/0aebcd4f-b2b5-4bbc-b8f8-a10c4b8f2c17");

        List<ShowsImage> showsImage = new ArrayList<>();
        showsImage.add(ShowsImage.builder().s3Key(fileUrl.get(0)).type("대표").showsInfo(showsInfo).build());
        showsImage.add(ShowsImage.builder().s3Key(fileUrl.get(1)).type("일반").showsInfo(showsInfo).build());

        showsInfo.addShowsImage(showsImage);
        ShowsCategory showsCategory = ShowsCategory.builder().name("공연").build();
        showsInfo.updateShowsCategory(showsCategory);

        // when
        given(showsService.createShowsInfo(any(ShowsInfoCreateRequest.class))).willReturn(showsInfo);
        given(showsService.createShowsImage(any(), any())).willReturn(showsImage);
        given(showsService.createShowsCategory(any())).willReturn(showsCategory);
        adminService.createShowsBundle(showsInfoCreateRequest, mock());

        // then
        verify(showsService, times(1)).createShowsInfo(any(ShowsInfoCreateRequest.class));
        verify(showsService, times(1)).createShowsImage(any(), any(ShowsInfo.class));
        verify(showsService, times(1)).createShowsCategory(any());

        assertEquals(showsImage.get(0).getS3Key(), showsInfo.getShowsImage().get(0).getS3Key());
        assertEquals(showsImage.get(1).getS3Key(), showsInfo.getShowsImage().get(1).getS3Key());
        assertEquals(showsImage.get(0).getType(), showsInfo.getShowsImage().get(0).getType());
        assertEquals(showsImage.get(1).getType(), showsInfo.getShowsImage().get(1).getType());
        assertEquals(showsCategory.getName(), showsInfo.getShowsCategory().getName());

    }

    @Test
    void 등급_생성_테스트() {
        // given
        Place place = placeCreateRequest.toEntity(200);
        ShowsInfo showsInfo = showsInfoCreateRequest.toEntity();
        Shows shows = showsCreateRequest.toEntity(place, showsInfo);
        Grade grade = gradeCreateRequest.toEntity(shows);

        // when
        given(showsService.findByShowsId(any())).willReturn(shows);
        given(gradeService.createGrade(any(GradeCreateRequest.class), any(Shows.class))).willReturn(grade);
        adminService.createGrade(1L, gradeCreateRequest);

        // then
        verify(showsService, times(1)).findByShowsId(any());
        verify(gradeService, times(1)).createGrade(any(GradeCreateRequest.class), any(Shows.class));
        assertEquals(gradeCreateRequest.name(), grade.getName());
        assertEquals(gradeCreateRequest.normalPrice(), grade.getNormalPrice());
        assertEquals(gradeCreateRequest.auctionPrice(), grade.getAuctionPrice());
        assertEquals(grade.getShows(), shows);
        assertEquals(grade.getShows().getShowsInfo(), showsInfo);
    }

    @Test
    void 등급_구역_생성_테스트() {
        // given
        Place place = placeCreateRequest.toEntity(100);
        ShowsInfo showsInfo = showsInfoCreateRequest.toEntity();
        Shows shows = showsCreateRequest.toEntity(place, showsInfo);
        Grade grade = gradeCreateRequest.toEntity(shows);
        Zone zone =
                Zone
                        .builder()
                        .name("A")
                        .seatNumber(100)
                        .build();
        place.updateZone(List.of(zone));
        ZoneGrade zoneGrade = zoneGradeCreateRequest.toEntity(zone, grade);

        // when
        given(zoneService.getReferenceById(anyLong())).willReturn(zone);
        given(gradeService.getReferenceById(anyLong())).willReturn(grade);
        given(
                zoneGradeService
                        .createZoneGrade(
                                any(ZoneGradeCreateRequest.class),
                                any(Zone.class),
                                any(Grade.class)))
                .willReturn(zoneGrade);

        ZoneGradeCreateResponse zoneGradeCreateResponse = adminService.createZoneGrade(zoneGradeCreateRequest);

        // then
        verify(zoneService, times(1)).getReferenceById(anyLong());
        verify(gradeService, times(1)).getReferenceById(anyLong());
        verify(zoneGradeService, times(1))
                .createZoneGrade(
                        any(ZoneGradeCreateRequest.class),
                        any(Zone.class),
                        any(Grade.class)
                );
        assertEquals(zoneGrade.getGrade(), grade);
        assertEquals(zoneGrade.getZone(), zone);
        assertEquals(zoneGradeCreateResponse.getGradeName(), zoneGrade.getGrade().getName());
        assertEquals(zoneGradeCreateResponse.getAuctionPrice(), zoneGrade.getGrade().getAuctionPrice());
    }
}