package me.kimgunwoo.auctionseats.domain.place.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;


public record PlacesSeatInfo (
        @Size(min = 1, max = 10, message = "필수로 해당 구역을 입력해야 합니다.")
        String zone,

        @NotNull(message = "필수로 좌석번호를 입력해야 합니다.")
        Integer seatNumber
){}
