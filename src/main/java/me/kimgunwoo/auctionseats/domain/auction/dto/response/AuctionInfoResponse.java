package me.kimgunwoo.auctionseats.domain.auction.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.querydsl.core.annotations.QueryProjection;

import java.time.LocalDateTime;

public record AuctionInfoResponse(
        Long id,
        Boolean isEnded,
        @JsonFormat(pattern = "yyyy-MM-dd")
        LocalDateTime startDateTime
) {
    @QueryProjection
    public AuctionInfoResponse(Long id, Boolean isEnded, LocalDateTime startDateTime) {
        this.id = id;
        this.isEnded = isEnded;
        this.startDateTime = startDateTime;
    }
}