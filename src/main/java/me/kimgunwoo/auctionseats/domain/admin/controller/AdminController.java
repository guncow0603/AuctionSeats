package me.kimgunwoo.auctionseats.domain.admin.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.kimgunwoo.auctionseats.domain.admin.adminService.AdminServiceImpl;
import me.kimgunwoo.auctionseats.domain.admin.dto.request.PlacesRequest;
import me.kimgunwoo.auctionseats.domain.admin.dto.request.ShowsRequest;
import me.kimgunwoo.auctionseats.domain.admin.dto.response.PlacesResponse;
import me.kimgunwoo.auctionseats.global.dto.EmptyObject;
import me.kimgunwoo.auctionseats.global.response.ApiResponse;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static me.kimgunwoo.auctionseats.global.exception.SuccessCode.SUCCESS_PLACE_AND_ZONE_CREATE;
import static me.kimgunwoo.auctionseats.global.exception.SuccessCode.SUCCESS_SHOWS_AND_SCHEDULE_CREATE;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Slf4j
public class AdminController {

    private final AdminServiceImpl adminService;

    // 공연장 및 구역 생성
    @PostMapping("/admin/places")
    public ResponseEntity<ApiResponse<List<PlacesResponse>>> createPlaceAndZone(
            @Valid @RequestBody PlacesRequest placeRequest
    ) {
        List<PlacesResponse> placeResponseList = adminService.createPlaceAndZone(placeRequest);
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
    public ResponseEntity<ApiResponse<EmptyObject>> createShowsBundleAndSchedule(
            @Valid @RequestPart ShowsRequest showsRequest,
            @RequestPart(value = "files", required = false) List<MultipartFile> multipartFiles,
            @PathVariable Long placeId
    ) {
        adminService.createShowsBundleAndSchedule(placeId, showsRequest, multipartFiles);
        return ResponseEntity
                .status(SUCCESS_SHOWS_AND_SCHEDULE_CREATE.getHttpStatus())
                .body(
                        ApiResponse.of(
                                SUCCESS_SHOWS_AND_SCHEDULE_CREATE.getCode(),
                                SUCCESS_SHOWS_AND_SCHEDULE_CREATE.getMessage()
                        )
                );
    }

}