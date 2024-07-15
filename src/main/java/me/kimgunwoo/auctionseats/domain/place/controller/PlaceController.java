package me.kimgunwoo.auctionseats.domain.place.controller;

import lombok.RequiredArgsConstructor;
import me.kimgunwoo.auctionseats.domain.place.dto.response.PlaceGetResponse;
import me.kimgunwoo.auctionseats.domain.place.service.PlaceService;
import me.kimgunwoo.auctionseats.global.response.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static me.kimgunwoo.auctionseats.global.exception.SuccessCode.SUCCESS_GET_ALL_PLACE;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class PlaceController {
    private final PlaceService placeService;

    // 공연장 전체 조회
    @GetMapping("/places")
    public ResponseEntity<ApiResponse<List<PlaceGetResponse>>> getAllPlace() {
        List<PlaceGetResponse> placeGetResponses = placeService.getAllPlace();
        return ResponseEntity
                .status(
                        SUCCESS_GET_ALL_PLACE.getHttpStatus())
                .body(
                        ApiResponse.of(SUCCESS_GET_ALL_PLACE.getCode(),
                                SUCCESS_GET_ALL_PLACE.getMessage(),
                                placeGetResponses)
                );
    }

}