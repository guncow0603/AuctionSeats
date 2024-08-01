package me.kimgunwoo.auctionseats.domain.reservation.reservation_seat.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import me.kimgunwoo.auctionseats.domain.reservation.reservation_seat.entity.ReservationSeat;
import me.kimgunwoo.auctionseats.domain.seat.dto.response.ReservedSeatResponse;
import org.springframework.stereotype.Repository;

import java.util.List;

import static me.kimgunwoo.auctionseats.domain.reservation.reservation_seat.entity.QReservationSeat.reservationSeat;


@Repository
@RequiredArgsConstructor
public class ReservationSeatQueryRepositoryImpl implements ReservationSeatQueryRepository {

    private final JPAQueryFactory query;

    @Override
    public List<ReservationSeat> findReservationSeatsByReservationId(Long reservationId) {
        return query.select(reservationSeat)
                .from(reservationSeat)
                .where(reservationSeat.reservation.id.eq(reservationId))
                .fetch();
    }


    @Override
    public List<ReservedSeatResponse> findReservedSeats(Long scheduleId) {
        List<ReservedSeatResponse> result = query
                .select(Projections.constructor(
                        ReservedSeatResponse.class,
                        reservationSeat.id.zoneGradeId,
                        reservationSeat.id.seatNumber
                ))
                .from(reservationSeat)
                .where(reservationSeat.id.scheduleId.eq(scheduleId))
                .fetch();
        return result;
    }
}