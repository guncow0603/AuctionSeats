package me.kimgunwoo.auctionseats.domain.reservation.repository;

import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import me.kimgunwoo.auctionseats.domain.place.entity.Places;
import me.kimgunwoo.auctionseats.domain.reservation.dto.ReservationSeatInfo;
import me.kimgunwoo.auctionseats.domain.reservation.dto.response.ReservationDetailResponse;
import me.kimgunwoo.auctionseats.domain.reservation.dto.response.ReservationResponse;
import me.kimgunwoo.auctionseats.domain.reservation.entity.Reservation;
import me.kimgunwoo.auctionseats.domain.reservation.reservation_seat.entity.ReservationSeat;
import me.kimgunwoo.auctionseats.domain.schedule.entity.Schedule;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static me.kimgunwoo.auctionseats.domain.place.entity.QPlaces.places;
import static me.kimgunwoo.auctionseats.domain.reservation.entity.QReservation.reservation;
import static me.kimgunwoo.auctionseats.domain.reservation.reservation_seat.entity.QReservationSeat.reservationSeat;
import static me.kimgunwoo.auctionseats.domain.schedule.entity.QSchedule.schedule;
import static me.kimgunwoo.auctionseats.domain.show.entity.QShows.shows;
import static me.kimgunwoo.auctionseats.domain.user.entity.QUser.user;

@RequiredArgsConstructor
@Repository
public class ReservationQueryRepositoryImpl implements ReservationQueryRepository {

    private final JPAQueryFactory query;

    @Override
    public Long countReservationSeats(Long userId, Long scheduleId) {
        return query.select(reservationSeat.count()).from(reservationSeat)
                .join(reservationSeat.schedule, schedule)
                .join(reservationSeat.reservation, reservation)
                .where(reservation.user.id.eq(userId), schedule.id.eq(scheduleId))
                .fetchOne();
    }

    @Override
    public Optional<ReservationDetailResponse> getReservationDetailResponse(Long reservationId) {
        // 예약 번호(예약), 예매자(유저) / 제목(공연), useDate(회차) address(공연장), seats(구역)
        Reservation reservation1 = query.select(reservation)
                .from(reservation)
                .innerJoin(reservation.user, user).fetchJoin()
                .where(reservation.id.eq(reservationId))
                .fetchOne();

        List<ReservationSeat> reservationSeats = query.select(reservationSeat)
                .from(reservationSeat)
                .innerJoin(reservationSeat.schedule, schedule).fetchJoin()
                .innerJoin(schedule.shows, shows).fetchJoin()
                .innerJoin(shows.places, places).fetchJoin()
                .where(reservationSeat.reservation.id.eq(reservation1.getId()))
                .fetch();
        List<ReservationSeatInfo> seatInfos = new ArrayList<>();
        reservationSeats.forEach(
                seat -> {
                    seatInfos.add(new ReservationSeatInfo(
                            seat.getZoneGrade().getZone().getName(),
                            seat.getId().getSeatNumber())
                    );
                }
        );

        Schedule schedule1 = reservationSeats.get(0).getSchedule();
        Places place1 = reservationSeats.get(0).getSchedule().getShows().getPlaces();

        ReservationDetailResponse.builder()
                .username(reservation1.getUser().getName())
                .title(schedule1.getShows().getTitle())
                .seats(seatInfos)
                .useDate(schedule1.getStartDateTime())
                .reservationId(reservation1.getId())
                .address(place1.getAddress())
                .build();

        return null;
    }

    @Override
    public Page<ReservationResponse> getReservationsResponse(Long userId, Pageable pageable) {
        // 예약번호(예약), 예매일(예약), 상태(예약), 매수(예약좌석) / 제목(공연), 이용일(회차),  취소가능일(회차),
        List<Reservation> reservations = query.select(reservation)
                .from(reservation)
                .innerJoin(reservation.user, user)
                .where(reservation.user.id.eq(userId))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Reservation> countQuery = query.selectFrom(reservation)
                .innerJoin(reservation.user, user)
                .where(reservation.user.id.eq(userId));

        List<ReservationResponse> content = reservations.stream().map(reservation1 -> {
            return ReservationResponse.builder()
                    .title(reservation1.getSchedule().getShows().getTitle())
                    .reservationId(reservation1.getId())
                    .reservationDate(reservation1.getCreatedAt())
                    .status(reservation1.getStatus())
                    .numberOfTicket(reservation1.getReservationAmount())
                    .useDate(reservation1.getSchedule().getStartDateTime())
                    .cancelDeadline(reservation1.getSchedule().getStartDateTime())
                    .build();
        }).toList();

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchCount);
    }

}
