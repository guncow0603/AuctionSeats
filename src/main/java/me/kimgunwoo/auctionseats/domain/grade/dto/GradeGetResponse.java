package me.kimgunwoo.auctionseats.domain.grade.dto;

import lombok.Getter;
import me.kimgunwoo.auctionseats.domain.grade.entity.Grade;

@Getter
public class GradeGetResponse {
    private final Long gradeId;

    private final String name;

    private final Long normalPrice;

    private final Long auctionPrice;

    public GradeGetResponse(Grade grade) {
        this.gradeId = grade.getId();
        this.name = grade.getName();
        this.normalPrice = grade.getNormalPrice();
        this.auctionPrice = grade.getAuctionPrice();
    }
}
