package me.kimgunwoo.auctionseats.domain.admin.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.kimgunwoo.auctionseats.domain.admin.adminService.AdminServiceImpl;
import me.kimgunwoo.auctionseats.domain.admin.dto.request.PlacesRequest;
import me.kimgunwoo.auctionseats.domain.admin.dto.request.ShowsRequest;
import me.kimgunwoo.auctionseats.domain.admin.dto.request.ShowsSequenceSeatRequest;
import me.kimgunwoo.auctionseats.domain.admin.dto.response.PlacesResponse;
import me.kimgunwoo.auctionseats.global.dto.EmptyObject;
import me.kimgunwoo.auctionseats.global.response.ApiResponse;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static me.kimgunwoo.auctionseats.global.exception.SuccessCode.*;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Slf4j
public class AdminController {

    private final AdminServiceImpl adminService;

    // 공연장
    @PostMapping("/admin/places")
    public ResponseEntity<ApiResponse<List<PlacesResponse>>> createPlace(
            @Valid @RequestBody PlacesRequest placeRequest) {
        List<PlacesResponse> placeResponse = adminService.createPlace(placeRequest);
        return ResponseEntity
                .status(SUCCESS_PLACE_AND_SEAT_CREATE.getHttpStatus())
                .body(
                        ApiResponse.of(
                                SUCCESS_PLACE_AND_SEAT_CREATE.getCode(),
                                SUCCESS_PLACE_AND_SEAT_CREATE.getMessage(),
                                placeResponse
                        )
                );
    }
    // 공연 및 이미지, 카테고리, 회차
    @PostMapping(value = "/admin/places/{placeId}/shows",
            consumes = {
                    MediaType.APPLICATION_JSON_VALUE,
                    MediaType.MULTIPART_FORM_DATA_VALUE
            })
    public ResponseEntity<ApiResponse<EmptyObject>> createShowsAndSequence(
            @Valid @RequestPart ShowsRequest showsRequest,
            @RequestPart(value = "files", required = false) List<MultipartFile> multipartFileList,
            @PathVariable Long placeId
    ) {
        adminService.createShowsAndSequence(showsRequest, placeId, multipartFileList);
        return ResponseEntity
                .status(SUCCESS_SHOWS_AND_SEQUENCE_CREATE.getHttpStatus())
                .body(
                        ApiResponse.of(
                                SUCCESS_SHOWS_AND_SEQUENCE_CREATE.getCode(),
                                SUCCESS_SHOWS_AND_SEQUENCE_CREATE.getMessage()
                        )
                );
    }

    // 공연별 회차
    @PostMapping("/admin/shows_sequence_seat")
    public ResponseEntity<ApiResponse<EmptyObject>> createShowsSequenceSeatAndAuction(
            @RequestParam Long placeId,
            @RequestParam Long sequenceId,
            @RequestBody ShowsSequenceSeatRequest showsSequenceSeatRequest) {

        adminService.createShowsSequenceSeatAndAuction(placeId, sequenceId, showsSequenceSeatRequest);

        return ResponseEntity
                .status(SUCCESS_SHOWS_SEQUENCE_SEAT_AND_AUCTION_CREATE.getHttpStatus())
                .body(ApiResponse.of(
                                SUCCESS_SHOWS_SEQUENCE_SEAT_AND_AUCTION_CREATE.getCode(),
                                SUCCESS_SHOWS_SEQUENCE_SEAT_AND_AUCTION_CREATE.getMessage()
                        )
                );
    }
}