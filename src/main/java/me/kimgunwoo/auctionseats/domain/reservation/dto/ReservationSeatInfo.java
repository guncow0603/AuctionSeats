package me.kimgunwoo.auctionseats.domain.reservation.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ReservationSeatInfo {

    private String zone;

    private Integer seatNumber;
}
