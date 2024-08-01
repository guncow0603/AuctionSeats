package me.kimgunwoo.auctionseats.domain.admin.dto.request;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import me.kimgunwoo.auctionseats.domain.show.entity.AgeGrade;
import me.kimgunwoo.auctionseats.domain.show.entity.ShowsCategory;
import me.kimgunwoo.auctionseats.domain.show.entity.ShowsInfo;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Set;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
public class ShowsInfoCreateRequestTest {

    private static ValidatorFactory factory;
    private static Validator validator;

    String showsName;

    String showsDescription;

    Integer ageGrade;

    Integer runningTime;

    String categoryName;

    ShowsInfoCreateRequest showsInfoCreateRequest;

    @BeforeAll
    public static void init() {
        factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();

    }

    @BeforeEach
    void createInit() {
        this.showsName = "힙합 쇼케이스";
        this.showsDescription = "힙합의 신나는 공연";
        this.ageGrade = 12;
        this.runningTime = 360;
        this.categoryName = "공연";
    }

    @Test
    void 공연이름_검증_테스트() {
        // given
        ShowsInfoCreateRequest showsInfoCreateRequest = new ShowsInfoCreateRequest(
                "",
                this.showsDescription,
                this.ageGrade,
                this.runningTime,
                this.categoryName);

        // when
        Set<ConstraintViolation<ShowsInfoCreateRequest>> violations = validator.validate(showsInfoCreateRequest);

        // then
        assertThat(violations).isNotEmpty();
        violations
                .forEach(
                        error -> {
                            assertThat(error.getMessage()).isEqualTo("1~30자 사이로 입력해주세요");
                        });

    }

    @Test
    void 공연내용_검증_테스트() {
        // given
        ShowsInfoCreateRequest showsInfoCreateRequest = new ShowsInfoCreateRequest(
                this.showsName,
                "",
                this.ageGrade,
                this.runningTime,
                this.categoryName);

        // when
        Set<ConstraintViolation<ShowsInfoCreateRequest>> violations = validator.validate(showsInfoCreateRequest);

        // then
        assertThat(violations).isNotEmpty();
        violations
                .forEach(
                        error -> {
                            assertThat(error.getMessage()).isEqualTo("1~150자 사이로 입력해주세요");
                        });

    }

    @Test
    void 공연_공연시간_검증_테스트() {
        // given
        ShowsInfoCreateRequest showsInfoCreateRequest = new ShowsInfoCreateRequest(
                this.showsName,
                this.showsDescription,
                this.ageGrade,
                null,
                this.categoryName);

        // when
        Set<ConstraintViolation<ShowsInfoCreateRequest>> violations = validator.validate(showsInfoCreateRequest);

        // then
        assertFalse(violations.isEmpty());
        violations
                .forEach(
                        error -> {
                            assertThat(error.getMessage()).isEqualTo("공연 시간은 필수입니다");
                        });

    }

    @Test
    void 공연_카테고리_검증_테스트() {
        // given
        ShowsInfoCreateRequest showsInfoCreateRequest = new ShowsInfoCreateRequest(
                this.showsName,
                this.showsDescription,
                this.ageGrade,
                this.runningTime,
                "");

        // when
        Set<ConstraintViolation<ShowsInfoCreateRequest>> violations = validator.validate(showsInfoCreateRequest);

        // then
        assertThat(violations).isNotEmpty();
        violations
                .forEach(
                        error -> {
                            assertThat(error.getMessage()).isEqualTo("카테고리 입력은 필수입니다.");
                        });

    }

    @Test
    void 공연정보_Entity_생성_성공_테스트() {
        // given
        ShowsInfoCreateRequest showsInfoCreateRequest = new ShowsInfoCreateRequest(
                this.showsName,
                this.showsDescription,
                this.ageGrade,
                this.runningTime,
                this.categoryName);

        //when
        ShowsInfo showsInfo = showsInfoCreateRequest.toEntity();

        ShowsCategory showsCategory =
                ShowsCategory
                        .builder()
                        .name(this.categoryName)
                        .build();
        showsInfo.updateShowsCategory(showsCategory);

        //then
        assertEquals(this.showsName, showsInfo.getName());
        assertEquals(this.showsDescription, showsInfo.getDescription());
        assertEquals(AgeGrade.AGE_12, showsInfo.getAgeGrade());
        assertEquals(this.runningTime, showsInfo.getRunningTime());
        assertEquals(this.categoryName, showsInfo.getShowsCategory().getName());

    }

}