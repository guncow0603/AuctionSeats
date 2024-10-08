package me.kimgunwoo.auctionseats.domain.reservation.reservation_seat.repository;

import me.kimgunwoo.auctionseats.domain.reservation.reservation_seat.entity.ReservationSeat;
import me.kimgunwoo.auctionseats.domain.seat.dto.response.ReservedSeatResponse;

import java.util.List;

public interface ReservationSeatQueryRepository {

    /**
     * 예약에 포함된 예약 좌석들의 목록을 조회한다.
     * @param reservationId
     * @return List<ReservationSeatId>
     */
    List<ReservationSeat> findReservationSeatsByReservationId(Long reservationId);

    // 회차에 예약된 좌석들의 목록을 조회한다.
    List<ReservedSeatResponse> findReservedSeats(Long scheduleId);

    /**
     * 회차에 예매된 좌석들의 목록을 캐시에서 조회한다.
     * @param scheduleId
     * @return
     */
    List<ReservedSeatResponse> findReservedSeatsFromCache(Long scheduleId);
}
