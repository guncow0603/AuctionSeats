package me.kimgunwoo.auctionseats.domain.place.dto.response;

import me.kimgunwoo.auctionseats.domain.place.entity.Place;

public record PlaceGetResponse(
        Long placeId,
        String name
) {
    public PlaceGetResponse(Place place) {
        this(place.getId(), place.getName());
    }
}
