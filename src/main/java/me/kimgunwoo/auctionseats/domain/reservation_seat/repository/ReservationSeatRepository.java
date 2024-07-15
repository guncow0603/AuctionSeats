package me.kimgunwoo.auctionseats.domain.reservation_seat.repository;

import me.kimgunwoo.auctionseats.domain.reservation_seat.entity.ReservationSeat;
import me.kimgunwoo.auctionseats.domain.reservation_seat.entity.ReservationSeatId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationSeatRepository
        extends JpaRepository<ReservationSeat, ReservationSeatId>, ReservationSeatQueryRepository {
}