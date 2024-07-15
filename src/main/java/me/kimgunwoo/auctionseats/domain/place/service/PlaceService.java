package me.kimgunwoo.auctionseats.domain.place.service;

import me.kimgunwoo.auctionseats.domain.admin.dto.ZoneInfo;
import me.kimgunwoo.auctionseats.domain.admin.dto.request.PlacesRequest;
import me.kimgunwoo.auctionseats.domain.place.dto.response.PlaceGetResponse;
import me.kimgunwoo.auctionseats.domain.place.entity.Places;

import java.util.List;

public interface PlaceService {
    // 공연장 생성
    Places createPlace(PlacesRequest placesRequest);

    // 공연장 총 좌석 개수 계산
    Integer calculateSeats(List<ZoneInfo> seats);

    // 공연장 프록시 객체 조회
    Places getReferenceById(Long placeId);

    // 공연장 전체 조회
    List<PlaceGetResponse> getAllPlace();
}
