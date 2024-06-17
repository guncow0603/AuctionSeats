package me.kimgunwoo.auctionseats.domain.place.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class PlacesRequest {
    @Size(max = 30)
    @NotBlank
    private final String name;

    @Size(max = 150)
    @NotBlank
    private final String address;

    @NotBlank
    private final Integer countSeats;

    @Valid
    @NotBlank
    private final List<PlacesSeatInfo> seats;
}
