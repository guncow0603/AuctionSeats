package me.kimgunwoo.auctionseats.domain.admin.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import me.kimgunwoo.auctionseats.domain.grade.entity.Grade;
import me.kimgunwoo.auctionseats.domain.show.entity.Shows;


public record GradeCreateRequest(
        @Size(min = 1, max = 30, message = "1~30자 사이로 입력해주세요.")
        String name,

        @NotNull(message = "일반 가격 정보는 필수 입니다.")
        Long normalPrice,

        @NotNull(message = "경매 가격 정보는 필수 입니다.")
        Long auctionPrice
) {
    public Grade toEntity(Shows shows) {
        return Grade
                .builder()
                .name(this.name)
                .normalPrice(normalPrice)
                .auctionPrice(auctionPrice)
                .shows(shows)
                .build();
    }
}
