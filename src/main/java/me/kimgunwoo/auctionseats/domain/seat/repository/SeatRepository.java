package me.kimgunwoo.auctionseats.domain.seat.repository;

import me.kimgunwoo.auctionseats.domain.seat.entity.Seat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface SeatRepository extends JpaRepository<Seat, Long> {

    @Query("select s from Seat s join fetch s.places where s.id = :seatId")
    Optional<Seat> findSeatWithPlaceById(Long seatId);
}
