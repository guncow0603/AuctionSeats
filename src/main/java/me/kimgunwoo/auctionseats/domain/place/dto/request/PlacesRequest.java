package me.kimgunwoo.auctionseats.domain.place.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;


public record PlacesRequest(
        @Size(min = 1, max = 30, message = "공연장 이름은 필수입니다.")
        String name,

        @Size(min = 1, max = 150, message = "주소 입력은 필수입니다.")
        String address,

        @Valid
        @NotNull(message = "좌석 정보는 필수입니다.")
        List<PlaceSeatAuctionInfo> seats
) {}
