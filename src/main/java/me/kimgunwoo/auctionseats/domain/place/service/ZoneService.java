package me.kimgunwoo.auctionseats.domain.place.service;

import me.kimgunwoo.auctionseats.domain.place.dto.ZoneInfo;
import me.kimgunwoo.auctionseats.domain.place.dto.response.ZoneGetResponse;
import me.kimgunwoo.auctionseats.domain.place.entity.Zone;

import java.util.List;

public interface ZoneService {

    // 공연장 구역 생성
    List<Zone> createZone(List<ZoneInfo> zoneInfos);

    // 구역 프록시 객체 생성
    Zone getReferenceById(Long zoneId);

    // 공연장 구역 전체 조회
    List<ZoneGetResponse> getAllZone(Long placeId);

    // 해당 공연의 공연장 구역 전체 조회
    List<ZoneGetResponse> getAllZoneFromShows(Long showsId);
}
