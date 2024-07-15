package me.kimgunwoo.auctionseats.domain.reservation_seat.repository;

import me.kimgunwoo.auctionseats.domain.reservation_seat.entity.ReservationSeat;
import me.kimgunwoo.auctionseats.domain.seat.dto.ReservedSeatResponse;

import java.util.List;

public interface ReservationSeatQueryRepository {

    /**
     * 예약에 포함된 예약 좌석들의 목록을 조회한다.
     * @param reservationId
     * @return List<ReservationSeatId>
     */
    List<ReservationSeat> findReservationSeatsByReservationId(Long reservationId);
}