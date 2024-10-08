package me.kimgunwoo.auctionseats.domain.place.controller;

import lombok.RequiredArgsConstructor;
import me.kimgunwoo.auctionseats.domain.place.dto.response.ZoneGetResponse;
import me.kimgunwoo.auctionseats.domain.place.service.ZoneService;
import me.kimgunwoo.auctionseats.global.response.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static me.kimgunwoo.auctionseats.global.exception.SuccessCode.SUCCESS_GET_ALL_ZONE;
import static me.kimgunwoo.auctionseats.global.exception.SuccessCode.SUCCESS_GET_ALL_ZONE_FROM_SHOWS;

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

    // 해당 공연의 공연장 구역 전체 조회
    @GetMapping("/zones")
    public ResponseEntity<ApiResponse<List<ZoneGetResponse>>> getAllZoneFromShows(@RequestParam Long showsId) {
        List<ZoneGetResponse> zoneGetResponses = zoneService.getAllZoneFromShows(showsId);
        return ResponseEntity
                .status(
                        SUCCESS_GET_ALL_ZONE_FROM_SHOWS.getHttpStatus())
                .body(
                        ApiResponse.of(
                                SUCCESS_GET_ALL_ZONE_FROM_SHOWS.getCode(),
                                SUCCESS_GET_ALL_ZONE_FROM_SHOWS.getMessage(), zoneGetResponses)
                );
    }
}