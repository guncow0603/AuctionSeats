package me.kimgunwoo.auctionseats.domain.seat.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public record SeatRequest(
        @NotBlank
        String zone,
       @NotNull
       Integer seatNumber) {


}
