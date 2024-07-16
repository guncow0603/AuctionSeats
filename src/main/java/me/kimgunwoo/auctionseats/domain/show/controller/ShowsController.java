package me.kimgunwoo.auctionseats.domain.show.controller;

import lombok.RequiredArgsConstructor;
import me.kimgunwoo.auctionseats.domain.show.dto.response.ShowsInfoGetResponse;
import me.kimgunwoo.auctionseats.domain.show.service.ShowsInfoService;
import me.kimgunwoo.auctionseats.global.response.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static me.kimgunwoo.auctionseats.global.exception.SuccessCode.SUCCESS_GET_SHOWS_INFO;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class ShowsController {

    private final ShowsInfoService showsInfoService;

    // 공연 정보 단건 조회
    @GetMapping("/shows-info/{showsInfoId}")
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
}
