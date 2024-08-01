package me.kimgunwoo.auctionseats.domain.admin.dto.request;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import me.kimgunwoo.auctionseats.domain.grade.entity.Grade;
import me.kimgunwoo.auctionseats.domain.grade.entity.ZoneGrade;
import me.kimgunwoo.auctionseats.domain.place.entity.Place;
import me.kimgunwoo.auctionseats.domain.place.entity.Zone;
import me.kimgunwoo.auctionseats.domain.show.entity.Shows;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Set;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class ZoneGradeCreateRequestTest {

    private static ValidatorFactory factory;
    private static Validator validator;

    Long zoneId;

    Long gradeId;

    @BeforeAll
    public static void init() {
        factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @BeforeEach
    public void createInit() {
        this.zoneId = 1L;
        this.gradeId = 1L;
    }

    @Test
    void 구역_아이디_검증_테스트() {
        // given
        ZoneGradeCreateRequest zoneGradeCreateRequest = new ZoneGradeCreateRequest(null, this.gradeId);

        // when
        Set<ConstraintViolation<ZoneGradeCreateRequest>> violations = validator.validate(zoneGradeCreateRequest);

        // then
        assertThat(violations).isNotEmpty();
        violations
                .forEach(
                        error -> {
                            assertThat(error.getMessage()).isEqualTo("구역 Id값은 필수입니다.");
                        });
    }

    @Test
    void 등급_아이디_검증_테스트() {
        // given
        ZoneGradeCreateRequest zoneGradeCreateRequest = new ZoneGradeCreateRequest(this.zoneId, null);

        // when
        Set<ConstraintViolation<ZoneGradeCreateRequest>> violations = validator.validate(zoneGradeCreateRequest);

        // then
        assertThat(violations).isNotEmpty();
        violations
                .forEach(
                        error -> {
                            assertThat(error.getMessage()).isEqualTo("등급 Id값은 필수입니다.");
                        });
    }

    @Test
    void 구역_등급_Entity_생성_테스트() {
        ZoneGradeCreateRequest zoneGradeCreateRequest = new ZoneGradeCreateRequest(this.zoneId, this.gradeId);
        Place place = Mockito.mock();
        Shows shows = Mockito.mock();
        Zone zone =
                Zone
                        .builder()
                        .name("A")
                        .seatNumber(100)
                        .build();

        Grade grade =
                Grade
                        .builder()
                        .name("VIP")
                        .auctionPrice(70000L)
                        .normalPrice(100000L)
                        .shows(shows)
                        .build();
        place.updateZone(List.of(zone));
        ZoneGrade zoneGrade = zoneGradeCreateRequest.toEntity(zone, grade);

        // when
        assertEquals(zoneGrade.getZone(), zone);
        assertEquals(zoneGrade.getGrade(), grade);

    }

}