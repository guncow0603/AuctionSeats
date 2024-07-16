package me.kimgunwoo.auctionseats.domain.place.controller;

import lombok.RequiredArgsConstructor;
import me.kimgunwoo.auctionseats.domain.place.dto.response.ZoneGetResponse;
import me.kimgunwoo.auctionseats.domain.place.service.ZoneService;
import me.kimgunwoo.auctionseats.global.response.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static me.kimgunwoo.auctionseats.global.exception.SuccessCode.SUCCESS_GET_ALL_ZONE;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class ZoneController {

    private final ZoneService zoneService;

    // 공연장 구역 전체 조회
    @GetMapping("/places/{placeId}/zones")
    public ResponseEntity<ApiResponse<List<ZoneGetResponse>>> getAllZone(@PathVariable Long placeId) {
        List<ZoneGetResponse> zoneGetResponses = zoneService.getAllZone(placeId);
        return ResponseEntity
                .status(SUCCESS_GET_ALL_ZONE.getHttpStatus())
                .body(
                        ApiResponse.of(
                                SUCCESS_GET_ALL_ZONE.getCode(),
                                SUCCESS_GET_ALL_ZONE.getMessage(),
                                zoneGetResponses)
                );
    }
}