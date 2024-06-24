package me.kimgunwoo.auctionseats.domain.admin.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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

import static me.kimgunwoo.auctionseats.global.exception.SuccessCode.SUCCESS_PLACE_CREATE;

@RestController
@RequestMapping("api/v1")
@RequiredArgsConstructor
public class AdminController {

    private final AdminServiceImpl adminService;

    // 공연장 추가
    @PostMapping("/admin/place")
    public ResponseEntity<ApiResponse<List<PlacesResponse>>> createPlace(
            @Valid @RequestBody PlacesRequest placeRequest) {
        List<PlacesResponse> placeResponse = adminService.createPlace(placeRequest);
        return ResponseEntity
                .status(SUCCESS_PLACE_CREATE.getHttpStatus())
                .body(
                        new ApiResponse<>(
                                SUCCESS_PLACE_CREATE.getCode(),
                                SUCCESS_PLACE_CREATE.getMessage(),
                                placeResponse
                        )
                );
    }

}