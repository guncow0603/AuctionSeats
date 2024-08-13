package me.kimgunwoo.auctionseats.domain.reservation.reservation_seat.repository;

import me.kimgunwoo.auctionseats.domain.reservation.reservation_seat.entity.ReservationSeat;
import me.kimgunwoo.auctionseats.domain.reservation.reservation_seat.entity.ReservationSeatId;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface ReservationSeatRepository
        extends JpaRepository<ReservationSeat, ReservationSeatId>, ReservationSeatQueryRepository {

    @EntityGraph(attributePaths = {"schedule"})
    Slice<ReservationSeat> findByScheduleStartDateTimeGreaterThan(LocalDateTime startDateTime, Pageable pageable);
}
