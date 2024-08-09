package me.kimgunwoo.auctionseats.domain.admin.service;

import me.kimgunwoo.auctionseats.domain.admin.dto.request.*;
import me.kimgunwoo.auctionseats.domain.admin.dto.response.*;
import me.kimgunwoo.auctionseats.domain.auction.dto.request.AuctionCreateRequest;
import me.kimgunwoo.auctionseats.domain.place.entity.Zone;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface AdminService {

    // 공연장 및 구역 생성
    List<PlaceCreateResponse> createPlaceAndZone(PlaceCreateRequest placeCreateRequest);

    // 공연장 및 구역 응답 생성
    List<PlaceCreateResponse> createPlaceResponse(List<Zone> zoneList);

    //  공연과 관련된 공연 정보, 공연 카테고리, 공연 이미지 생성
    ShowsInfoCreateResponse createShowsBundle(ShowsInfoCreateRequest showsInfoCreateRequest,
                                              List<MultipartFile> multipartFiles);

    // 공연 및 회차 생성
    ShowsCreateResponse createShowsAndSchedule(ShowsCreateRequest showsCreateRequest, Long showsInfoId, Long placeId);

    // 등급 생성
    GradeCreateResponse createGrade(Long showsId, GradeCreateRequest gradeCreateRequest);

    // 구역 등급 생성
    ZoneGradeCreateResponse createZoneGrade(ZoneGradeCreateRequest zoneGradeCreateRequest);

    // 경매 생성
    void createAuction(Long scheduleId, Long zoneGradeId, AuctionCreateRequest auctionCreateRequest);
}