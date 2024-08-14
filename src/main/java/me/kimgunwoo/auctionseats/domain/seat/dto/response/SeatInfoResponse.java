package me.kimgunwoo.auctionseats.domain.seat.dto.response;

import lombok.Builder;

public record SeatInfoResponse(
        String zoneName,
        String gradeName,
        Long price,
        Long zoneGradeId
) {
    @Builder
    public SeatInfoResponse {}
}

