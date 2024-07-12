package me.kimgunwoo.auctionseats.domain.admin.adminService;

import me.kimgunwoo.auctionseats.domain.admin.dto.request.GradeRequest;
import me.kimgunwoo.auctionseats.domain.admin.dto.request.PlacesRequest;
import me.kimgunwoo.auctionseats.domain.admin.dto.request.ShowsRequest;
import me.kimgunwoo.auctionseats.domain.admin.dto.request.ZoneGradeRequest;
import me.kimgunwoo.auctionseats.domain.admin.dto.response.GradeResponse;
import me.kimgunwoo.auctionseats.domain.admin.dto.response.PlacesResponse;
import me.kimgunwoo.auctionseats.domain.admin.dto.response.ShowsResponse;
import me.kimgunwoo.auctionseats.domain.admin.dto.response.ZoneGradeResponse;
import me.kimgunwoo.auctionseats.domain.place.entity.Zone;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface AdminService {
    // 공연장 및 구역 생성
    List<PlacesResponse> createPlaceAndZone(PlacesRequest placesRequest);

    // 공연장 및 구역 응답 생성
    List<PlacesResponse> createPlaceResponse(List<Zone> zoneList);

    //  공연과 관련된 공연 정보, 공연 카테고리, 공연 이미지, 공연 및 회차 생성
    ShowsResponse createShowsBundleAndSchedule(Long placeId, ShowsRequest showsRequest,
                                               List<MultipartFile> multipartFiles);
    // 등급 생성
    GradeResponse createGrade(Long goodsId, GradeRequest gradeRequest);


    ZoneGradeResponse createZoneGrade(ZoneGradeRequest zoneGradeRequest);
}