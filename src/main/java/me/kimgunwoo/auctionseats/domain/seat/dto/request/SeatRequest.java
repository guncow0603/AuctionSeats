package me.kimgunwoo.auctionseats.domain.seat.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;


public record SeatRequest(
        @NotBlank(message = "필수로 해당 구역을 입력해야 합니다.")
        String zone,
        @NotNull(message = "필수로 좌석번호를 입력해야 합니다.")
       Integer seatNumber) {


}
