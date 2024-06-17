package me.kimgunwoo.auctionseats.domain.seat.repository;

import me.kimgunwoo.auctionseats.domain.seat.entity.Seat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SeatRepository extends JpaRepository<Seat,Long> {
}
