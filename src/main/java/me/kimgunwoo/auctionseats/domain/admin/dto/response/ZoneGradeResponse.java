package me.kimgunwoo.auctionseats.domain.admin.dto.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import me.kimgunwoo.auctionseats.domain.grade.entity.Grade;

@Getter
@RequiredArgsConstructor
public class ZoneGradeResponse {
    private final String gradeName;

    private final Long auctionPrice;

    public ZoneGradeResponse(Grade grade) {
        this.gradeName = grade.getName();
        this.auctionPrice = grade.getAuctionPrice();
    }
}
