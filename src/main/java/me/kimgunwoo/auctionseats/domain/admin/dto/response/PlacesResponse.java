package me.kimgunwoo.auctionseats.domain.admin.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import me.kimgunwoo.auctionseats.domain.place.entity.Places;

public record PlacesResponse(String zone, Integer zoneCountSeat, Long placeId) {
    public PlacesResponse(String zone, Integer zoneCountSeat, Places places) {
        this(zone, zoneCountSeat, places.getId());
    }
}