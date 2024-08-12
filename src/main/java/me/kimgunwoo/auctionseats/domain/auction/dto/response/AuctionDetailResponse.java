package me.kimgunwoo.auctionseats.domain.auction.dto.response;

import me.kimgunwoo.auctionseats.domain.auction.entity.Auction;

import java.time.LocalDateTime;

public record AuctionDetailResponse(
        Long id,
        Long bidPrice,
        Long startPrice,
        LocalDateTime endDateTime
) {
    public static AuctionDetailResponse from(Auction entity, Long bidPrice) {
        return new AuctionDetailResponse(
                entity.getId(),
                bidPrice,
                entity.getStartPrice(),
                entity.getEndDateTime()
        );
    }
}