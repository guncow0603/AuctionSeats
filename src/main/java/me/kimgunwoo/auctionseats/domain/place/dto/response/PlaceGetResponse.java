package me.kimgunwoo.auctionseats.domain.place.dto.response;

import lombok.Getter;
import me.kimgunwoo.auctionseats.domain.place.entity.Place;

@Getter
public class PlaceGetResponse {
    private final Long placeId;

    private final String name;

    public PlaceGetResponse(Place place) {
        this.placeId = place.getId();
        this.name = place.getName();
    }
}