package me.kimgunwoo.auctionseats.domain.place.dto.response;

import me.kimgunwoo.auctionseats.domain.place.entity.Zone;

public record ZoneGetResponse(
        Long zoneId,
        String name,
        Integer seatNumber
) {
    public ZoneGetResponse(Zone zone) {
        this(zone.getId(), zone.getName(), zone.getSeatNumber());
    }
}


