package me.kimgunwoo.auctionseats.domain.seat.dto.response;

import lombok.Builder;

public record ReservedSeatResponse(
        Long zoneGradeId,
        Integer seatNumber
) {
    @Builder
    public ReservedSeatResponse {}
}

