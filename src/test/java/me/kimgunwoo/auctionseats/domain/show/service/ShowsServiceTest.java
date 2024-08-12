package me.kimgunwoo.auctionseats.domain.show.service;

import static me.kimgunwoo.auctionseats.domain.admin.service.AdminServiceImpl.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import me.kimgunwoo.auctionseats.domain.show.entity.Shows;
import me.kimgunwoo.auctionseats.domain.show.entity.ShowsCategory;
import me.kimgunwoo.auctionseats.domain.show.entity.ShowsImage;
import me.kimgunwoo.auctionseats.domain.show.entity.ShowsInfo;
import me.kimgunwoo.auctionseats.domain.show.repository.ShowsInfoRepository;
import me.kimgunwoo.auctionseats.domain.show.repository.ShowsRepository;
import me.kimgunwoo.auctionseats.domain.show.dto.response.*;
import me.kimgunwoo.auctionseats.domain.place.entity.Place;


@ExtendWith(MockitoExtension.class)
public class ShowsServiceTest {

    @InjectMocks
    ShowsServiceImpl ShowsService;

    @Mock
    ShowsRepository ShowsRepository;

    @Mock
    ShowsInfoRepository ShowsInfoRepository;

    List<ShowsInfo> ShowsInfoList;

    List<Shows> ShowsList;

    List<Place> placeList;

    @BeforeEach
    void init() {
        // given
        this.ShowsInfoList = new ArrayList<>(List.of(ShowsInfo
                        .builder()
                        .name("공연1")
                        .description("신나는 코드 작성 시간")
                        .runningTime(360)
                        .ageGrade(15)
                        .build(),
                ShowsInfo
                        .builder()
                        .name("공연2")
                        .description("새벽 코딩")
                        .runningTime(360)
                        .ageGrade(0)
                        .build()
        )
        );

        ReflectionTestUtils.setField(this.ShowsInfoList.get(0), "id", 1L);
        ReflectionTestUtils.setField(this.ShowsInfoList.get(1), "id", 2L);

        List<String> fileUrl = new ArrayList<>();
        fileUrl.add("Shows/thumbnail/1/asd");
        fileUrl.add("Shows/general/1/zxc");
        fileUrl.add("Shows/general/1/qwe");
        fileUrl.add("Shows/general/1/rty");

        List<ShowsImage> ShowsImageList1 = new ArrayList<>();
        ShowsImageList1.add(
                ShowsImage.builder().s3Key(fileUrl.get(0)).type("대표").showsInfo(this.ShowsInfoList.get(0)).build());
        ShowsImageList1.add(
                ShowsImage.builder().s3Key(fileUrl.get(1)).type("일반").showsInfo(this.ShowsInfoList.get(0)).build());

        List<ShowsImage> ShowsImageList2 = new ArrayList<>();
        ShowsImageList2.add(
                ShowsImage.builder().s3Key(fileUrl.get(2)).type("대표").showsInfo(this.ShowsInfoList.get(1)).build());
        ShowsImageList2.add(
                ShowsImage.builder().s3Key(fileUrl.get(3)).type("대표").showsInfo(this.ShowsInfoList.get(1)).build());

        this.ShowsInfoList.get(0).addShowsImage(ShowsImageList1);
        ShowsCategory ShowsCategory1 = ShowsCategory.builder().name("연극").build();
        this.ShowsInfoList.get(0).updateShowsCategory(ShowsCategory1);
        ReflectionTestUtils.setField(this.ShowsInfoList.get(0), "id", 1L);

        this.ShowsInfoList.get(1).addShowsImage(ShowsImageList2);
        ShowsCategory ShowsCategory2 = ShowsCategory.builder().name("뮤지컬").build();
        this.ShowsInfoList.get(1).updateShowsCategory(ShowsCategory2);
        ReflectionTestUtils.setField(this.ShowsInfoList.get(1), "id", 2L);

        this.placeList = new ArrayList<>(List.of(Place
                        .builder()
                        .name("공연장1")
                        .address("주소1")
                        .countSeats(100)
                        .build(),
                Place
                        .builder()
                        .name("공연장2")
                        .address("주소2")
                        .countSeats(100)
                        .build()
        )
        );
        ReflectionTestUtils.setField(this.placeList.get(0), "id", 1L);
        ReflectionTestUtils.setField(this.placeList.get(1), "id", 2L);

        this.ShowsList = new ArrayList<>(
                List.of(
                        Shows
                                .builder()
                                .title("공연1 - 서울")
                                .endDate(LocalDate.of(2024, 2, 7))
                                .startDate(LocalDate.of(2024, 2, 8))
                                .showsInfo(this.ShowsInfoList.get(0))
                                .place(this.placeList.get(0))
                                .build(),
                        Shows
                                .builder()
                                .title("공연2 - 부산")
                                .endDate(LocalDate.of(2024, 2, 7))
                                .startDate(LocalDate.of(2024, 2, 8))
                                .showsInfo(this.ShowsInfoList.get(0))
                                .place(this.placeList.get(0))
                                .build())
        );

        ReflectionTestUtils.setField(this.ShowsList.get(0), "id", 1L);
        ReflectionTestUtils.setField(this.ShowsList.get(1), "id", 2L);

    }

    @Test
    void 공연_정보_전체_조회_테스트() {
        // when
        given(ShowsInfoRepository.findAll()).willReturn(this.ShowsInfoList);
        List<ShowsInfoGetResponse> ShowsInfoGetResponseList = ShowsService.getAllShowsInfo();

        //then
        assertEquals(ShowsInfoGetResponseList.get(0).getName(), this.ShowsInfoList.get(0).getName());
        assertEquals(
                ShowsInfoGetResponseList.get(0).getS3Url(),
                S3_PATH + this.ShowsInfoList.get(0).getShowsImage().get(0).getS3Key());
        assertEquals(ShowsInfoGetResponseList.get(0).getShowsInfoId(), this.ShowsInfoList.get(0).getId());

    }

    @Test
    void 공연_단건_조회_테스트() {

        //when
        given(ShowsRepository.findById(anyLong())).willReturn(Optional.ofNullable(this.ShowsList.get(0)));
        ShowsGetResponse ShowsGetResponse = ShowsService.getShows(1L);

        verify(ShowsRepository, times(1)).findById(anyLong());
        assertEquals(ShowsGetResponse.getShowsId(), ShowsList.get(0).getId());
        assertEquals(ShowsGetResponse.getTitle(), ShowsList.get(0).getTitle());
        assertEquals(ShowsGetResponse.getStartDate(), ShowsList.get(0).getStartDate());
        assertEquals(ShowsGetResponse.getEndDate(), ShowsList.get(0).getEndDate());
        assertEquals(ShowsGetResponse.getRunningTime(), ShowsList.get(0).getShowsInfo().getRunningTime());
        assertEquals(ShowsGetResponse.getAgeGrade(), ShowsList.get(0).getShowsInfo().getAgeGrade().getKorea());
        assertEquals(ShowsGetResponse.getPlaceId(), ShowsList.get(0).getPlace().getId());
        assertEquals(ShowsGetResponse.getPlaceName(), ShowsList.get(0).getPlace().getName());
        assertEquals(ShowsGetResponse.getPlaceAddress(), ShowsList.get(0).getPlace().getAddress());
        assertEquals(
                ShowsGetResponse.getS3Urls().get(0),
                S3_PATH + ShowsList.get(0).getShowsInfo().getShowsImage().get(0).getS3Key());

    }
}
