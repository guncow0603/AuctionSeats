package me.kimgunwoo.auctionseats.domain.reservation.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import me.kimgunwoo.auctionseats.domain.reservation.reservation_seat.dto.request.ReservationSeatCreateRequest;

import java.util.List;

@Builder
public record ReservationCreateRequest(
        @NotNull(message = "가격을 입력하세요.")
        Long price,

        @NotEmpty(message = "좌석 정보를 입력하세요.")
        List<@Valid ReservationSeatCreateRequest> reservationSeats
) {}
