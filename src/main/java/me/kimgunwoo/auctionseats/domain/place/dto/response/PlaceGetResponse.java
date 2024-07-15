package me.kimgunwoo.auctionseats.domain.place.dto.response;

import lombok.Getter;
import me.kimgunwoo.auctionseats.domain.place.entity.Places;

@Getter
public class PlaceGetResponse {
    private final Long placeId;

    private final String name;

    public PlaceGetResponse(Places places) {
        this.placeId = places.getId();
        this.name = places.getName();
    }
}