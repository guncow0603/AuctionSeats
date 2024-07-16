package me.kimgunwoo.auctionseats.domain.show.controller;

import lombok.RequiredArgsConstructor;
import me.kimgunwoo.auctionseats.domain.show.dto.response.ShowsInfoGetResponse;
import me.kimgunwoo.auctionseats.domain.show.dto.response.ShowsInfoGetSliceResponse;
import me.kimgunwoo.auctionseats.domain.show.service.ShowsInfoService;
import me.kimgunwoo.auctionseats.global.response.ApiResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static me.kimgunwoo.auctionseats.global.exception.SuccessCode.SUCCESS_GET_SHOWS_INFO;
import static me.kimgunwoo.auctionseats.global.exception.SuccessCode.SUCCESS_GET_SLICE_SHOWS_INFO;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class ShowsController {

    private final ShowsInfoService showsInfoService;

    // 공연 정보 단건 조회
    @GetMapping("/shows-infos/{showsInfoId}")
    public ResponseEntity<ApiResponse<ShowsInfoGetResponse>> getShowsInfo(@PathVariable Long showsInfoId) {
        ShowsInfoGetResponse showsInfoGetResponse = showsInfoService.getShowsInfo(showsInfoId);
        return ResponseEntity
                .status(SUCCESS_GET_SHOWS_INFO.getHttpStatus())
                .body(
                        ApiResponse.of(
                                SUCCESS_GET_SHOWS_INFO.getCode(),
                                SUCCESS_GET_SHOWS_INFO.getMessage(),
                                showsInfoGetResponse)
                );
    }

    @GetMapping("/shows-infos/slices")
    public ResponseEntity<ApiResponse<ShowsInfoGetSliceResponse>> getSliceShowsInfo(Pageable pageable,
                                                                                    @RequestParam(value = "categoryName", required = false) String categoryName) {
        ShowsInfoGetSliceResponse showsInfoGetSliceResponse = showsInfoService.getSliceShowsInfo(pageable,
                categoryName);
        return ResponseEntity
                .status(
                        SUCCESS_GET_SLICE_SHOWS_INFO.getHttpStatus())
                .body(
                        ApiResponse.of(
                                SUCCESS_GET_SLICE_SHOWS_INFO.getCode(),
                                SUCCESS_GET_SLICE_SHOWS_INFO.getMessage(),
                                showsInfoGetSliceResponse));
    }
}
