package me.kimgunwoo.auctionseats.domain.reservation.reservation_seat.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import me.kimgunwoo.auctionseats.domain.reservation.reservation_seat.entity.ReservationSeatId;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
public class ReservationSeatCreateRequest {

    @NotNull(message = "회차 id를 입력하세요.")
    private Long scheduleId;

    @NotBlank(message = "구역 등급 id를 입력하세요.")
    private Long zoneGradeId;

    @NotNull(message = "좌석 번호를 입력하세요.")
    private Integer seatNumber;

    public ReservationSeatId toId() {
        return new ReservationSeatId(scheduleId, zoneGradeId, seatNumber);
    }
}
