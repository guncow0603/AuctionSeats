package me.kimgunwoo.auctionseats.domain.reservation.repository;

import me.kimgunwoo.auctionseats.domain.reservation.entity.Reservation;
import me.kimgunwoo.auctionseats.domain.show.entity.ShowsImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReservationRepository extends JpaRepository<Reservation,Long> {
    Optional<ShowsImage> findThumbnailByShowsId(Long showsId);
}
