package me.kimgunwoo.auctionseats.domain.admin.dto.response;

import me.kimgunwoo.auctionseats.domain.place.entity.Place;

public record PlaceCreateResponse(String zone, Integer zoneCountSeat, Long placeId) {
    public PlaceCreateResponse(String zone, Integer zoneCountSeat, Place place) {
        this(zone, zoneCountSeat, place.getId());
    }
}