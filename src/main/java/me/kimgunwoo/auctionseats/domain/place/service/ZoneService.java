package me.kimgunwoo.auctionseats.domain.place.service;

import me.kimgunwoo.auctionseats.domain.admin.dto.ZoneInfo;
import me.kimgunwoo.auctionseats.domain.place.entity.Places;
import me.kimgunwoo.auctionseats.domain.place.entity.Zone;

import java.util.List;

public interface ZoneService {

    // 공연장 구역 생성
    public List<Zone> createZone(Places places, List<ZoneInfo> zoneInfos);
}
