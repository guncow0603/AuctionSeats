package me.kimgunwoo.auctionseats.domain.seat.dto.response;

import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class SeatInfoResponse {

    private String zoneName;

    private String gradeName;

    private Long price;

    private Long zoneGradeId;
}
