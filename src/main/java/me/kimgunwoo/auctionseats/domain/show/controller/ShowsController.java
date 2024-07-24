package me.kimgunwoo.auctionseats.domain.show.controller;

import lombok.RequiredArgsConstructor;
import me.kimgunwoo.auctionseats.domain.admin.dto.response.ShowsGetResponse;
import me.kimgunwoo.auctionseats.domain.show.dto.response.ShowsGetSliceResponse;
import me.kimgunwoo.auctionseats.domain.show.dto.response.ShowsInfoGetResponse;
import me.kimgunwoo.auctionseats.domain.show.service.ShowsService;
import me.kimgunwoo.auctionseats.domain.user.entity.User;
import me.kimgunwoo.auctionseats.global.annotaion.CurrentUser;
import me.kimgunwoo.auctionseats.global.response.ApiResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static me.kimgunwoo.auctionseats.global.exception.SuccessCode.*;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class ShowsController {

    private final ShowsService showsService;

    // 공연 정보 전체 조회
    @GetMapping("shows-infos")
    public ResponseEntity<ApiResponse<List<ShowsInfoGetResponse>>> getAllShowsInfo(@CurrentUser User user) {
        List<ShowsInfoGetResponse> showsInfoGetResponseList = showsService.getAllShowsInfo();
        return ResponseEntity
                .status(
                        SUCCESS_GET_ALL_SHOWS_INFO.getHttpStatus())
                .body(
                        ApiResponse.of(
                                SUCCESS_GET_ALL_SHOWS_INFO.getCode(),
                                SUCCESS_GET_ALL_SHOWS_INFO.getMessage(),
                                showsInfoGetResponseList));
    }

    // 공연 단건 조회
    @GetMapping("/shows/{showsId}")
    public ResponseEntity<ApiResponse<ShowsGetResponse>> getShows(@PathVariable Long showsId) {
        ShowsGetResponse showsGetResponse = showsService.getShows(showsId);
        return ResponseEntity
                .status(
                        SUCCESS_GET_SHOWS.getHttpStatus())
                .body(
                        ApiResponse.of(
                                SUCCESS_GET_SHOWS.getCode(),
                                SUCCESS_GET_SHOWS.getMessage(),
                                showsGetResponse)
                );
    }

    // 공연 페이징 전체 조회
    @GetMapping("/shows")
    public ResponseEntity<ApiResponse<ShowsGetSliceResponse>> getAllShows(
            Pageable pageable,
            @RequestParam(value = "categoryName", required = false) String categoryName) {
        ShowsGetSliceResponse showsGetSliceResponse = showsService.getSliceShows(pageable, categoryName);
        return ResponseEntity
                .status(SUCCESS_GET_SLICE_SHOWS.getHttpStatus())
                .body(
                        ApiResponse.of(
                                SUCCESS_GET_SLICE_SHOWS.getCode(),
                                SUCCESS_GET_SLICE_SHOWS.getMessage(),
                                showsGetSliceResponse)
                );
    }
}