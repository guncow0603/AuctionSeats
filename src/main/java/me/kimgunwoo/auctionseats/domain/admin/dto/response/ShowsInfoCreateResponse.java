package me.kimgunwoo.auctionseats.domain.admin.dto.response;

import lombok.Getter;

@Getter
public class ShowsInfoCreateResponse {
    private final Long showsInfoId;

    public ShowsInfoCreateResponse(Long showsInfoId) {
        this.showsInfoId = showsInfoId;
    }
}
