package me.kimgunwoo.auctionseats.domain.reservation.repository;

import me.kimgunwoo.auctionseats.domain.reservation.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<Reservation,Long> {
}