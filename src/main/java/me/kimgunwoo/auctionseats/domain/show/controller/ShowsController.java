package me.kimgunwoo.auctionseats.domain.show.controller;

import lombok.RequiredArgsConstructor;
import me.kimgunwoo.auctionseats.domain.reservation.reservation_seat.repository.ReservationSeatRepository;
import me.kimgunwoo.auctionseats.domain.seat.dto.response.ReservedSeatResponse;
import me.kimgunwoo.auctionseats.domain.show.dto.response.*;
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
    private final ReservationSeatRepository reservationSeatRepository;
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

    // 공연 카테고리 조회
    @GetMapping("/shows-categorys")
    public ResponseEntity<ApiResponse<List<ShowsCategoryGetResponse>>> getAllCategory() {
        List<ShowsCategoryGetResponse> showsCategoryGetResponseList = showsService.getAllShowsCategory();

        return ResponseEntity
                .status(
                        SUCCESS_GET_ALL_SHOWS_CATEGORY.getHttpStatus())
                .body(
                        ApiResponse.of(
                                SUCCESS_GET_ALL_SHOWS_CATEGORY.getCode(),
                                SUCCESS_GET_ALL_SHOWS_CATEGORY.getMessage(),
                                showsCategoryGetResponseList));

    }
    @GetMapping("/shows/{showsId}/seats")
    public ResponseEntity<ApiResponse<ShowsSeatInfoResponse>> getShowsSeatInfo(@PathVariable Long showsId) {
        ShowsSeatInfoResponse response = showsService.findShowsSeatInfo(showsId);
        return ResponseEntity
                .status(SUCCESS_GET_SHOWS_SEAT_INFO.getHttpStatus())
                .body(ApiResponse.of(
                        SUCCESS_GET_SHOWS_SEAT_INFO.getCode(),
                        SUCCESS_GET_SHOWS_SEAT_INFO.getMessage(),
                        response
                ));
    }

    @GetMapping("/shows/{showsId}/auction-seats")
    public ResponseEntity<ApiResponse<ShowsAuctionSeatInfoResponse>> getShowsAuctionSeatInfo(
            @PathVariable Long showsId,
            @RequestParam Long scheduleId
    ) {
        ShowsAuctionSeatInfoResponse response = showsService.findShowsAuctionSeatInfo(scheduleId, showsId);
        return ResponseEntity
                .status(SUCCESS_GET_SHOWS_AUCTION_INFO.getHttpStatus())
                .body(ApiResponse.of(
                        SUCCESS_GET_SHOWS_AUCTION_INFO.getCode(),
                        SUCCESS_GET_SHOWS_AUCTION_INFO.getMessage(),
                        response
                ));
    }

    @GetMapping("/shows/{scheduleId}/reserved-seats")
    public ResponseEntity<ApiResponse<List<ReservedSeatResponse>>> getReservedSeats(@PathVariable Long scheduleId) {
        List<ReservedSeatResponse> response = reservationSeatRepository.findReservedSeats(scheduleId);

        return ResponseEntity
                .status(SUCCESS_GET_SHOWS_RESERVED_SEAT_INFO.getHttpStatus())
                .body(ApiResponse.of(
                        SUCCESS_GET_SHOWS_RESERVED_SEAT_INFO.getCode(),
                        SUCCESS_GET_SHOWS_RESERVED_SEAT_INFO.getMessage(),
                        response
                ));
    }
}