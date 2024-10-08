package me.kimgunwoo.auctionseats.domain.reservation.reservation_seat.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import me.kimgunwoo.auctionseats.domain.reservation.reservation_seat.entity.ReservationSeatId;

@Builder
public record ReservationSeatCreateRequest(
        @NotNull(message = "회차 id를 입력하세요.")
        Long scheduleId,

        @NotBlank(message = "구역 등급 id를 입력하세요.")
        Long zoneGradeId,

        @NotNull(message = "좌석 번호를 입력하세요.")
        Integer seatNumber
) {
    public ReservationSeatId toId() {
        return new ReservationSeatId(scheduleId, zoneGradeId, seatNumber);
    }
}
