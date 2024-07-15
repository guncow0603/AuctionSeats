package me.kimgunwoo.auctionseats.domain.seat.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class ReservedSeatResponse {

    private Long zoneGradeId;

    private Integer seatNumber;
}
