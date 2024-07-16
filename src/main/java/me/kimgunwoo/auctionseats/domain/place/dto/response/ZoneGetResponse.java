package me.kimgunwoo.auctionseats.domain.place.dto.response;

import lombok.Getter;
import me.kimgunwoo.auctionseats.domain.place.entity.Zone;

@Getter
public class ZoneGetResponse {
    private final Long zoneId;

    private final String name;

    private final Integer seatNumber;

    public ZoneGetResponse(Zone zone) {
        this.zoneId = zone.getId();
        this.name = zone.getName();
        this.seatNumber = zone.getSeatNumber();
    }

}

