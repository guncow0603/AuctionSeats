package me.kimgunwoo.auctionseats.domain.admin.dto.request;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import me.kimgunwoo.auctionseats.domain.place.entity.Place;
import me.kimgunwoo.auctionseats.domain.show.entity.Shows;
import me.kimgunwoo.auctionseats.domain.show.entity.ShowsInfo;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Set;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class ShowsCreateRequestTest {

    private static ValidatorFactory factory;
    private static Validator validator;

    String title;

    LocalDate startDate;

    LocalDate endDate;

    LocalTime startTime;

    @BeforeAll
    public static void init() {
        factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();

    }

    @BeforeEach
    void createInit() {
        this.title = "레미제라블-서울";
        this.startDate = LocalDate.of(2023, 3, 1);
        this.endDate = LocalDate.of(2023, 4, 1);
        this.startTime = LocalTime.of(12, 30);
    }

    @Test
    void 공연_타이틀_빈공간_검증_테스트() {
        // given
        ShowsCreateRequest showsCreateRequest = new ShowsCreateRequest(
                null,
                this.startDate,
                this.endDate,
                this.startTime
        );

        //when
        Set<ConstraintViolation<ShowsCreateRequest>> violations = validator.validate(showsCreateRequest);

        //then
        assertFalse(violations.isEmpty());
        violations
                .forEach(
                        error -> {
                            assertThat(error.getMessage()).isEqualTo("공연제목 기입은 필수 입니다.");
                        });

    }

    @Test
    void 공연_타이틀_패턴_검증_테스트() {
        // given
        ShowsCreateRequest showsCreateRequest = new ShowsCreateRequest(
                "레미제라블서울",
                this.startDate,
                this.endDate,
                this.startTime
        );

        //when
        Set<ConstraintViolation<ShowsCreateRequest>> violations = validator.validate(showsCreateRequest);

        //then
        assertFalse(violations.isEmpty());
        violations
                .forEach(
                        error -> {
                            assertThat(error.getMessage()).isEqualTo("입력 양식: (공연제목) - (지역 및 목표) 입니다.");
                        });

    }

    @Test
    void 공연_시작날짜_검증_테스트() {
        // given
        ShowsCreateRequest showsCreateRequest = new ShowsCreateRequest(
                this.title,
                null,
                this.endDate,
                this.startTime
        );

        // when
        Set<ConstraintViolation<ShowsCreateRequest>> violations = validator.validate(showsCreateRequest);

        // then
        assertFalse(violations.isEmpty());
        violations
                .forEach(
                        error -> {
                            assertThat(error.getMessage()).isEqualTo("공연 시작일 기입은 필수입니다.");
                        });

    }

    @Test
    void 공연_종료날짜_검증_테스트() {
        // given
        ShowsCreateRequest showsCreateRequest = new ShowsCreateRequest(
                this.title,
                this.startDate,
                null,
                this.startTime
        );

        // when
        Set<ConstraintViolation<ShowsCreateRequest>> violations = validator.validate(showsCreateRequest);

        // then
        assertFalse(violations.isEmpty());
        violations
                .forEach(
                        error -> {
                            assertThat(error.getMessage()).isEqualTo("공연 종료일은 필수입니다.");
                        });

    }

    @Test
    void 공연_상연시간_검증_테스트() {
        // given
        ShowsCreateRequest showsCreateRequest = new ShowsCreateRequest(
                this.title,
                this.startDate,
                this.endDate,
                null
        );

        // when
        Set<ConstraintViolation<ShowsCreateRequest>> violations = validator.validate(showsCreateRequest);

        // then
        assertFalse(violations.isEmpty());
        violations
                .forEach(
                        error -> {
                            assertThat(error.getMessage()).isEqualTo("상영 시간은 필수입니다");
                        });

    }

    @Test
    void 공연_Entity_생성_성공_테스트() {
        // given
        ShowsCreateRequest showsCreateRequest = new ShowsCreateRequest(
                this.title,
                this.startDate,
                this.endDate,
                this.startTime
        );

        //when
        Place place = Mockito.mock();
        ShowsInfo showsInfo = Mockito.mock();
        Shows shows = showsCreateRequest.toEntity(place, showsInfo);

        //then
        assertEquals(this.title, shows.getTitle());
        assertEquals(this.startDate.getYear(), shows.getStartDate().getYear());
        assertEquals(this.startDate.getMonth(), shows.getStartDate().getMonth());
        assertEquals(this.startDate.getDayOfMonth(), shows.getStartDate().getDayOfMonth());
        assertEquals(this.endDate.getYear(), shows.getEndDate().getYear());
        assertEquals(this.endDate.getMonth(), shows.getEndDate().getMonth());
        assertEquals(this.endDate.getDayOfMonth(), shows.getEndDate().getDayOfMonth());
        assertEquals(showsInfo, shows.getShowsInfo());
        assertEquals(place, shows.getPlace());
    }
}