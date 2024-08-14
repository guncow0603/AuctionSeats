package me.kimgunwoo.auctionseats.domain.seat.dto.response;

import lombok.Builder;

import java.time.LocalDateTime;

public record AuctionSeatInfoResponse(
        String zoneName,
        String gradeName,
        Long price,
        Long zoneGradeId,
        Long auctionId,
        Integer seatNumber,
        LocalDateTime deadline,
        Boolean isEnded
) {
    @Builder
    public AuctionSeatInfoResponse {}
}

