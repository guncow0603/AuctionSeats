package me.kimgunwoo.auctionseats.domain.admin.adminService;

import me.kimgunwoo.auctionseats.domain.admin.dto.request.GradeCreateRequest;
import me.kimgunwoo.auctionseats.domain.admin.dto.request.PlaceCreateRequest;
import me.kimgunwoo.auctionseats.domain.admin.dto.request.ShowsCreateRequest;
import me.kimgunwoo.auctionseats.domain.admin.dto.request.ZoneGradeCreateRequest;
import me.kimgunwoo.auctionseats.domain.admin.dto.response.GradeCreateResponse;
import me.kimgunwoo.auctionseats.domain.admin.dto.response.PlaceCreateResponse;
import me.kimgunwoo.auctionseats.domain.admin.dto.response.ShowsCreateResponse;
import me.kimgunwoo.auctionseats.domain.admin.dto.response.ZoneGradeCreateResponse;
import me.kimgunwoo.auctionseats.domain.auction.dto.request.AuctionCreateRequest;
import me.kimgunwoo.auctionseats.domain.place.entity.Zone;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface AdminService {
    // 공연장 및 구역 생성
    List<PlaceCreateResponse> createPlaceAndZone(PlaceCreateRequest placesRequest);

    // 공연장 및 구역 응답 생성
    List<PlaceCreateResponse> createPlaceResponse(List<Zone> zoneList);

    //  공연과 관련된 공연 정보, 공연 카테고리, 공연 이미지, 공연 및 회차 생성
    ShowsCreateResponse createShowsBundleAndSchedule(Long placeId, ShowsCreateRequest showsRequest,
                                                     List<MultipartFile> multipartFiles);
    // 등급 생성
    GradeCreateResponse createGrade(Long goodsId, GradeCreateRequest gradeRequest);

    // 구역 등급 생성
    ZoneGradeCreateResponse createZoneGrade(ZoneGradeCreateRequest zoneGradeRequest);

    // 경매 생성
    void createAuction(Long scheduleId, Long zoneGradeId, AuctionCreateRequest auctionCreateRequest);
}