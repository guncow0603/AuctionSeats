package me.kimgunwoo.auctionseats.domain.place.service;

import me.kimgunwoo.auctionseats.domain.admin.dto.ZoneInfo;
import me.kimgunwoo.auctionseats.domain.admin.dto.request.PlacesRequest;
import me.kimgunwoo.auctionseats.domain.place.entity.Places;
import me.kimgunwoo.auctionseats.domain.place.entity.Zone;

import java.util.List;

public interface PlaceService {
    // 공연장 생성
    public Places createPlaces(PlacesRequest placeRequest);

    // 공연장 총 좌석 개수 계산
    public Integer calculateSeats(List<ZoneInfo> seats);

    // 공연장 구역 생성
    public List<Zone> createZone(Places place, List<ZoneInfo> zoneInfos);
}
