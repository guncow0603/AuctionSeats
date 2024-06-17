package me.kimgunwoo.auctionseats.domain.place.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;


public record PlacesSeatInfo (
        @Size(min = 1, max = 10)
        String zone,

        @NotBlank
        int seat_number
){}
