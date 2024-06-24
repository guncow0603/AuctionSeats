package me.kimgunwoo.auctionseats.domain.admin.adminService;

import me.kimgunwoo.auctionseats.domain.admin.dto.request.PlacesRequest;
import me.kimgunwoo.auctionseats.domain.admin.dto.response.PlacesResponse;

import java.util.List;

public interface AdminService {

    // 공연장 생성
    List<PlacesResponse> createPlace(PlacesRequest placeRequest);
}