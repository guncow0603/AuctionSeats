package me.kimgunwoo.auctionseats.domain.auction.dto.request;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import me.kimgunwoo.auctionseats.domain.grade.entity.Grade;
import me.kimgunwoo.auctionseats.domain.grade.entity.ZoneGrade;
import me.kimgunwoo.auctionseats.domain.schedule.entity.Schedule;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class AuctionCreateRequestTest {
    private static ValidatorFactory factory;
    private static Validator validator;

    @BeforeAll
    public static void init() {
        factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();

    }

    @Test
    void 경매_좌석_번호_검증() {
        AuctionCreateRequest request = new AuctionCreateRequest(
                null
        );

        //when
        var violations = validator.validate(request);

        //then
        assertThat(violations).isNotEmpty();
        violations.forEach(
                violation ->
                        assertThat(violation.getMessage()).isEqualTo("경매 좌석 번호를 입력해주세요.")
        );

    }

    @Test
    void 시작일시_포맷_검증() {
        AuctionCreateRequest request = new AuctionCreateRequest(
                1
        );

        // 현재 시간을 고정하여 사용합니다.
        LocalDateTime fixedNow = LocalDateTime.now().withSecond(0).withNano(0); // 초와 나노초를 0으로 설정하여 비교를 단순화합니다.
        Schedule schedule = Schedule.builder()
                .startDateTime(fixedNow)
                .build();

        ZoneGrade zoneGrade = ZoneGrade.builder()
                .grade(Grade.builder().auctionPrice(1000L).normalPrice(10000L).build())
                .build();

        // when
        var auction = request.toEntity(schedule, zoneGrade);

        // then
        var startDateTime = auction.getStartDateTime();

        // startDateTime이 fixedNow와 일치하는지 검증합니다.
        assertThat(startDateTime.getYear()).isEqualTo(fixedNow.getYear());
        assertThat(startDateTime.getMonthValue()).isEqualTo(fixedNow.getMonthValue());
        assertThat(startDateTime.getDayOfMonth()).isEqualTo(fixedNow.getDayOfMonth());
        assertThat(startDateTime.getHour()).isEqualTo(fixedNow.getHour());
        assertThat(startDateTime.getMinute()).isEqualTo(fixedNow.getMinute()); // `fixedNow`의 분과 비교
    }

    @Test
    void 마감일시_포맷_검증() {
        AuctionCreateRequest request = new AuctionCreateRequest(
                1
        );

        // 현재 시간으로 Schedule을 생성합니다.
        LocalDateTime now = LocalDateTime.now();
        Schedule schedule = Schedule.builder()
                .startDateTime(now)
                .build();

        ZoneGrade zoneGrade = ZoneGrade.builder()
                .grade(Grade.builder().auctionPrice(1000L).normalPrice(10000L).build())
                .build();

        // when
        var auction = request.toEntity(schedule, zoneGrade);

        // then
        // `endDateTime`이 `startDateTime`보다 1일 전이어야 합니다.
        var endDateTime = auction.getEndDateTime();
        var startDateTime = schedule.getStartDateTime();

        assertThat(endDateTime.getYear()).isEqualTo(startDateTime.getYear());
        assertThat(endDateTime.getMonthValue()).isEqualTo(startDateTime.getMonthValue());
        assertThat(endDateTime.getDayOfMonth()).isEqualTo(startDateTime.getDayOfMonth() - 1);
        assertThat(endDateTime.getHour()).isEqualTo(startDateTime.getHour());
        assertThat(endDateTime.getMinute()).isEqualTo(startDateTime.getMinute());
    }

}