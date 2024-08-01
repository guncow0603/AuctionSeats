package me.kimgunwoo.auctionseats.domain.seat.dto.response;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class AuctionSeatInfoResponse {

    private String zoneName;

    private String gradeName;

    private Long price;

    private Long zoneGradeId;

    private Long auctionId;

    private Integer seatNumber;

    private LocalDateTime deadline;

    private Boolean isEnded;
}
