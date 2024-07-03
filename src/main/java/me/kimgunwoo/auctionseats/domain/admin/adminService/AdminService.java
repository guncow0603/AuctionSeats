package me.kimgunwoo.auctionseats.domain.admin.adminService;

import me.kimgunwoo.auctionseats.domain.admin.dto.request.PlacesRequest;
import me.kimgunwoo.auctionseats.domain.admin.dto.request.ShowsRequest;
import me.kimgunwoo.auctionseats.domain.admin.dto.response.PlacesResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface AdminService {

    // 공연장 생성
    List<PlacesResponse> createPlace(PlacesRequest placeRequest);


    // 공연 생성
    void createShowsAndSequence(ShowsRequest showsRequest, Long placeId, List<MultipartFile> files);
}