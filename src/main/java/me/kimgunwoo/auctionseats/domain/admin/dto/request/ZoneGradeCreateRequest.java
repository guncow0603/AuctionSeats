package me.kimgunwoo.auctionseats.domain.admin.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import me.kimgunwoo.auctionseats.domain.grade.entity.Grade;
import me.kimgunwoo.auctionseats.domain.grade.entity.ZoneGrade;
import me.kimgunwoo.auctionseats.domain.place.entity.Zone;

@Getter
@RequiredArgsConstructor
public record ZoneGradeCreateRequest (
    @NotNull(message = "구역 Id값은 필수입니다.")
    Long zoneId,

    @NotNull(message = "등급 Id값은 필수입니다.")
    Long gradeId
){
    public ZoneGrade toEntity(Zone zone, Grade grade) {
        return ZoneGrade
                .builder()
                .zone(zone)
                .grade(grade)
                .build();
    }
}