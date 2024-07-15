package me.kimgunwoo.auctionseats.domain.reservation.repository;

import me.kimgunwoo.auctionseats.domain.reservation.entity.Reservation;
import me.kimgunwoo.auctionseats.global.exception.ApiException;
import me.kimgunwoo.auctionseats.global.exception.ErrorCode;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<Reservation, Long>, ReservationQueryRepository {

    default Reservation getById(Long id) {
        return findById(id).orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND_RESERVATION));
    }
}
