package me.kimgunwoo.auctionseats.domain.admin.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.kimgunwoo.auctionseats.domain.admin.adminService.AdminServiceImpl;
import me.kimgunwoo.auctionseats.domain.admin.dto.request.PlacesRequest;
import me.kimgunwoo.auctionseats.domain.admin.dto.request.ShowsRequest;
import me.kimgunwoo.auctionseats.domain.admin.dto.response.PlacesResponse;
import me.kimgunwoo.auctionseats.global.dto.EmptyObject;
import me.kimgunwoo.auctionseats.global.response.ApiResponse;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static me.kimgunwoo.auctionseats.global.exception.SuccessCode.SUCCESS_PLACE_CREATE;
import static me.kimgunwoo.auctionseats.global.exception.SuccessCode.SUCCESS_SHOWS_AND_SEQUENCE_CREATE;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Slf4j
public class AdminController {

    private final AdminServiceImpl adminService;

    // 공연장 추가
    @PostMapping("/admin/places")
    public ResponseEntity<ApiResponse<List<PlacesResponse>>> createPlace(
            @Valid @RequestBody PlacesRequest placeRequest) {
        List<PlacesResponse> placeResponse = adminService.createPlace(placeRequest);
        return ResponseEntity
                .status(SUCCESS_PLACE_CREATE.getHttpStatus())
                .body(
                        ApiResponse.of(
                                SUCCESS_PLACE_CREATE.getCode(),
                                SUCCESS_PLACE_CREATE.getMessage(),
                                placeResponse
                        )
                );
    }
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
}