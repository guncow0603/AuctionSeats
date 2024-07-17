package me.kimgunwoo.auctionseats.domain.admin.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.kimgunwoo.auctionseats.domain.admin.adminService.AdminServiceImpl;
import me.kimgunwoo.auctionseats.domain.admin.dto.request.GradeCreateRequest;
import me.kimgunwoo.auctionseats.domain.admin.dto.request.PlaceCreateRequest;
import me.kimgunwoo.auctionseats.domain.admin.dto.request.ShowsCreateRequest;
import me.kimgunwoo.auctionseats.domain.admin.dto.request.ZoneGradeCreateRequest;
import me.kimgunwoo.auctionseats.domain.admin.dto.response.GradeCreateResponse;
import me.kimgunwoo.auctionseats.domain.admin.dto.response.PlaceCreateResponse;
import me.kimgunwoo.auctionseats.domain.admin.dto.response.ShowsCreateResponse;
import me.kimgunwoo.auctionseats.domain.admin.dto.response.ZoneGradeCreateResponse;
import me.kimgunwoo.auctionseats.domain.auction.dto.request.AuctionCreateRequest;
import me.kimgunwoo.auctionseats.global.dto.EmptyObject;
import me.kimgunwoo.auctionseats.global.response.ApiResponse;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static me.kimgunwoo.auctionseats.global.exception.SuccessCode.*;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Slf4j
public class AdminController {
    private final AdminServiceImpl adminService;
    // 공연장 및 구역 생성
    @PostMapping("/admin/places")
    public ResponseEntity<ApiResponse<List<PlaceCreateResponse>>> createPlaceAndZone(
            @Valid @RequestBody PlaceCreateRequest placeRequest
    ) {
        List<PlaceCreateResponse> placeResponseList = adminService.createPlaceAndZone(placeRequest);
        return ResponseEntity
                .status(SUCCESS_PLACE_AND_ZONE_CREATE.getHttpStatus())
                .body(
                        ApiResponse.of(
                                SUCCESS_PLACE_AND_ZONE_CREATE.getCode(),
                                SUCCESS_PLACE_AND_ZONE_CREATE.getMessage(),
                                placeResponseList)
                );
    }
    //  공연과 관련된 공연 정보, 공연 카테고리, 공연 이미지, 공연 및 회차 생성
    @PostMapping(value = "/admin/places/{placeId}/shows",
            consumes = {
                    MediaType.APPLICATION_JSON_VALUE,
                    MediaType.MULTIPART_FORM_DATA_VALUE
            })
    public ResponseEntity<ApiResponse<ShowsCreateResponse>> createShowsBundleAndSchedule(
            @Valid @RequestPart ShowsCreateRequest showsRequest,
            @RequestPart(value = "files", required = false) List<MultipartFile> multipartFiles,
            @PathVariable Long placeId
    ) {
        ShowsCreateResponse showsResponse =
                adminService.createShowsBundleAndSchedule(
                        placeId,
                        showsRequest,
                        multipartFiles
                );
        return ResponseEntity
                .status(SUCCESS_SHOWS_AND_SCHEDULE_CREATE.getHttpStatus())
                .body(
                        ApiResponse.of(
                                SUCCESS_SHOWS_AND_SCHEDULE_CREATE.getCode(),
                                SUCCESS_SHOWS_AND_SCHEDULE_CREATE.getMessage(),
                                showsResponse
                        )
                );
    }
    // 등급 생성
    @PostMapping("/admin/shows/{showsId}/grades")
    public ResponseEntity<ApiResponse<GradeCreateResponse>> createGrade(
            @PathVariable Long showsId,
            @Valid @RequestBody GradeCreateRequest gradeRequest) {

        GradeCreateResponse gradeResponse = adminService.createGrade(showsId, gradeRequest);

        return ResponseEntity
                .status(SUCCESS_GRADE_CREATE.getHttpStatus())
                .body(
                        ApiResponse.of(
                                SUCCESS_GRADE_CREATE.getCode(),
                                SUCCESS_GRADE_CREATE.getMessage(),
                                gradeResponse)
                );
    }

    // 구역 등급 생성
    @PostMapping("/admin/zone-grades")
    public ResponseEntity<ApiResponse<ZoneGradeCreateResponse>> createZoneGrade(
            @RequestBody ZoneGradeCreateRequest zoneGradeRequest
    ) {
        ZoneGradeCreateResponse zoneGradeResponse = adminService.createZoneGrade(zoneGradeRequest);
        return ResponseEntity
                .status(SUCCESS_ZONE_GRADE_CREATE.getHttpStatus())
                .body(
                        ApiResponse.of(
                                SUCCESS_ZONE_GRADE_CREATE.getCode(),
                                SUCCESS_ZONE_GRADE_CREATE.getMessage(),
                                zoneGradeResponse
                        )
                );
    }

    // 경매 생성
    @PostMapping("/admin/schedules/{scheduleId}/auctions")
    public ResponseEntity<ApiResponse<EmptyObject>> createAuction(
            @PathVariable Long scheduleId,
            @RequestParam Long zoneGradeId,
            @RequestBody AuctionCreateRequest auctionCreateRequest
    ) {
        adminService.createAuction(scheduleId, zoneGradeId, auctionCreateRequest);
        return ResponseEntity
                .status(SUCCESS_AUCTION_CREATE.getHttpStatus())
                .body(
                        ApiResponse.of(
                                SUCCESS_AUCTION_CREATE.getCode(),
                                SUCCESS_AUCTION_CREATE.getMessage()
                        )
                );
    }
}