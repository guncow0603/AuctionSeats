package me.kimgunwoo.auctionseats.domain.admin.adminService;

import me.kimgunwoo.auctionseats.domain.admin.dto.request.PlacesRequest;
import me.kimgunwoo.auctionseats.domain.admin.dto.response.PlacesResponse;
import me.kimgunwoo.auctionseats.domain.place.entity.Zone;

import java.util.List;

public interface AdminService {
    // 공연장 및 구역 생성
    List<PlacesResponse> createPlaceAndZone(PlacesRequest placesRequest);

    // 공연장 및 구역 응답 생성
    List<PlacesResponse> createPlaceResponse(List<Zone> zoneList);
}