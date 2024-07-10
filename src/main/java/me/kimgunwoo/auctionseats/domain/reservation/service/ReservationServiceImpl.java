package me.kimgunwoo.auctionseats.domain.reservation.service;

import lombok.RequiredArgsConstructor;
import me.kimgunwoo.auctionseats.domain.reservation.dto.request.ReservationCreateRequest;
import me.kimgunwoo.auctionseats.domain.reservation.dto.response.ReservationDetailResponse;
import me.kimgunwoo.auctionseats.domain.reservation.entity.Reservation;
import me.kimgunwoo.auctionseats.domain.reservation.repository.ReservationRepository;
import me.kimgunwoo.auctionseats.domain.schedule.entity.Schedule;
import me.kimgunwoo.auctionseats.domain.schedule.repository.ScheduleRepository;
import me.kimgunwoo.auctionseats.domain.shows_sequence_seat.entity.ShowsScheduleSeat;
import me.kimgunwoo.auctionseats.domain.shows_sequence_seat.entity.ShowsScheduleSeatID;
import me.kimgunwoo.auctionseats.domain.shows_sequence_seat.repository.ShowsScheduleSeatRepository;
import me.kimgunwoo.auctionseats.domain.user.entity.User;
import me.kimgunwoo.auctionseats.domain.user.repository.UserRepository;
import me.kimgunwoo.auctionseats.global.exception.ApiException;
import me.kimgunwoo.auctionseats.global.exception.ErrorCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository reservationRepository;

    private final UserRepository userRepository;

    private final ShowsScheduleSeatRepository showsScheduleSeatRepository;

    private final ScheduleRepository sequenceRepository;

    private final SeatRepository seatRepository;

    @Override
    @Transactional
    public ReservationDetailResponse reserve(
            User user,
            Long seatId,
            Long sequenceId,
            ReservationCreateRequest request
    ) {
        // 좌석 예매 가능한지 확인
        ShowsScheduleSeatID showsScheduleSeatId = new ShowsScheduleSeatID(seatId, sequenceId);
        ShowsScheduleSeat showsScheduleSeat = showsScheduleSeatRepository.findById(showsScheduleSeatId)
                .orElseThrow(); // TODO: 예외 추가하기

        if (Boolean.TRUE.equals(showsScheduleSeat.getIsSelled())) {
            throw new ApiException(ErrorCode.ALREADY_RESERVED_SEAT);
        }

        // 클라이언트에서 전송한 금액이 실제로 결제할 금액과 같은지 확인
        if (!request.price().equals(showsScheduleSeat.getPrice())) {
            throw new ApiException(ErrorCode.INVALID_SEAT_PRICE);
        }

        // 유저 지갑 확인
        if (user.getPoint() < request.price()) {
            throw new ApiException(ErrorCode.NOT_ENOUGH_POINT);
        }

        // 결제
        User savedUser = userRepository.save(user);
        savedUser.usePoint(request.price());

        // 공연 정보 조회
        Schedule sequence = sequenceRepository.findScheduleWithShowsById(sequenceId)
                .orElseThrow(); // TODO: 예외 추가하기

        // 좌석 정보 조회
        Seat seat = seatRepository.findSeatWithPlaceById(seatId)
                .orElseThrow(); // TODO: 예외 추가하기

        // 예매 정보 생성
        Reservation reservation = request.toEntity(savedUser, showsScheduleSeat, request.price());
        Reservation savedReservation = reservationRepository.save(reservation);
        return ReservationDetailResponse.from(
                savedReservation.getId(),
                user.getName(),
                savedReservation.getCreatedAt(),
                sequence.getShows().getName(),
                sequence.getSchedule(),
                seat.getZone(),
                seat.getSeatNumber(),
                seat.getPlaces().getAddress(),
                sequence.getStartDateTime(),
                sequence.getShows().getShowsImage().get(0).getS3key() // TODO: S3 PREFIX 붙여야함
        );
    }
}
