package me.kimgunwoo.auctionseats.domain.grade.dto.response;

import me.kimgunwoo.auctionseats.domain.grade.entity.Grade;

public record GradeGetResponse(
        Long gradeId,
        String name,
        Long normalPrice,
        Long auctionPrice
) {
    public GradeGetResponse(Grade grade) {
        this(
                grade.getId(),
                grade.getName(),
                grade.getNormalPrice(),
                grade.getAuctionPrice()
        );
    }
}
