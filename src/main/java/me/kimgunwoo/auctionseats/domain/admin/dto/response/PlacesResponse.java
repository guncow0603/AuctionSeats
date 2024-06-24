package me.kimgunwoo.auctionseats.domain.admin.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import me.kimgunwoo.auctionseats.domain.place.entity.Places;

@Getter
@AllArgsConstructor
public class PlacesResponse {
    private final String zone;

    private final Integer zoneCountSeat;

    private final Long placeId;

    public PlacesResponse(String zone, Integer zoneCountSeat, Places places) {
        this.zone = zone;
        this.zoneCountSeat = zoneCountSeat;
        this.placeId = places.getId();
    }
}