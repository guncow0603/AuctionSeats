package me.kimgunwoo.auctionseats.domain.admin.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.kimgunwoo.auctionseats.domain.admin.adminService.AdminServiceImpl;
import me.kimgunwoo.auctionseats.domain.admin.dto.request.PlacesRequest;
import me.kimgunwoo.auctionseats.domain.admin.dto.response.PlacesResponse;
import me.kimgunwoo.auctionseats.global.response.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static me.kimgunwoo.auctionseats.global.exception.SuccessCode.SUCCESS_PLACE_AND_ZONE_CREATE;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Slf4j
public class AdminController {

    private final AdminServiceImpl adminService;

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

}