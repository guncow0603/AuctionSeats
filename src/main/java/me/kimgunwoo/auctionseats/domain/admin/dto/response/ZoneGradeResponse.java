package me.kimgunwoo.auctionseats.domain.admin.dto.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import me.kimgunwoo.auctionseats.domain.grade.entity.Grade;
import me.kimgunwoo.auctionseats.domain.grade.entity.ZoneGrade;

@Getter
@RequiredArgsConstructor
public class ZoneGradeResponse {
    private final String gradeName;

    private final Long auctionPrice;

    private final Long zoneGradeId;

    private final String zoneName;

    public ZoneGradeResponse(ZoneGrade zoneGrade) {
        this.gradeName = zoneGrade.getGrade().getName();
        this.auctionPrice = zoneGrade.getGrade().getAuctionPrice();
        this.zoneGradeId = zoneGrade.getId();
        this.zoneName = zoneGrade.getZone().getName();
    }
}
