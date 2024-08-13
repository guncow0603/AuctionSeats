package me.kimgunwoo.auctionseats.domain.seat.dto.response;

import lombok.Builder;
import lombok.Getter;
import java.time.LocalDateTime;

@Getter
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

