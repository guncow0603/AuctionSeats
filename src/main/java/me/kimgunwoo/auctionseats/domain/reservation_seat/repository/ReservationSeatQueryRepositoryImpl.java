package me.kimgunwoo.auctionseats.domain.reservation_seat.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import me.kimgunwoo.auctionseats.domain.reservation_seat.entity.ReservationSeat;
import org.springframework.stereotype.Repository;

import java.util.List;

import static me.kimgunwoo.auctionseats.domain.reservation_seat.entity.QReservationSeat.reservationSeat;


@Repository
@RequiredArgsConstructor
public class ReservationSeatQueryRepositoryImpl implements ReservationSeatQueryRepository {

    private JPAQueryFactory query;

    @Override
    public List<ReservationSeat> findReservationSeatsByReservationId(Long reservationId) {
        return query.select(reservationSeat)
                .from(reservationSeat)
                .where(reservationSeat.reservation.id.eq(reservationId))
                .fetch();
    }
}