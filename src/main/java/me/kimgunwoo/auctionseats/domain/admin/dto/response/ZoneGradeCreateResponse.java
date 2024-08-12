package me.kimgunwoo.auctionseats.domain.admin.dto.response;

import me.kimgunwoo.auctionseats.domain.grade.entity.ZoneGrade;

public record ZoneGradeCreateResponse(
        String gradeName,
        Long auctionPrice,
        Long zoneGradeId,
        String zoneName
) {
    public ZoneGradeCreateResponse(ZoneGrade zoneGrade) {
        this(
                zoneGrade.getGrade().getName(),
                zoneGrade.getGrade().getAuctionPrice(),
                zoneGrade.getId(),
                zoneGrade.getZone().getName()
        );
    }
}
