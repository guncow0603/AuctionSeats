package me.kimgunwoo.auctionseats.domain.place.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public record PlacesRequest(
        @Size(max = 30)
        @NotBlank
        String name,

        @Size(max = 150)
        @NotBlank
        String address,

        @NotBlank
        Integer countSeats,

        @Valid
        @NotBlank
        List<PlacesSeatInfo> seats
) {}
