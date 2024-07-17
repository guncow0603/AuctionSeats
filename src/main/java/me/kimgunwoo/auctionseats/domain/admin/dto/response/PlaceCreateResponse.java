package me.kimgunwoo.auctionseats.domain.admin.dto.response;

import me.kimgunwoo.auctionseats.domain.place.entity.Places;

public record PlaceCreateResponse(String zone, Integer zoneCountSeat, Long placeId) {
    public PlaceCreateResponse(String zone, Integer zoneCountSeat, Places places) {
        this(zone, zoneCountSeat, places.getId());
    }
}