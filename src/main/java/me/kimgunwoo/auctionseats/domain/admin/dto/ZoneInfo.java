package me.kimgunwoo.auctionseats.domain.admin.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;


public record ZoneInfo(
        @NotBlank(message = "구역 입력은 필수 입니다.")
        String zone,

        @NotNull(message = "구역당 총 좌석입력은 필 수 입니다.")
        Integer seatNumber
) { }