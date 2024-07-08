package me.kimgunwoo.auctionseats.domain.place.service;

import me.kimgunwoo.auctionseats.domain.place.entity.Places;

public interface PlaceService {
    // 공연장 저장
    Places savePlace(Places places);

    // 공연장 찾기
    Places findPlace(Long placeId);
}
