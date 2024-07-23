package me.kimgunwoo.auctionseats.domain.auction.controller;

import lombok.RequiredArgsConstructor;
import me.kimgunwoo.auctionseats.domain.auction.dto.response.AuctionDetailResponse;
import me.kimgunwoo.auctionseats.domain.auction.dto.response.AuctionInfoResponse;
import me.kimgunwoo.auctionseats.domain.auction.service.AuctionService;
import me.kimgunwoo.auctionseats.domain.user.entity.User;
import me.kimgunwoo.auctionseats.global.annotaion.CurrentUser;
import me.kimgunwoo.auctionseats.global.response.ApiResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static me.kimgunwoo.auctionseats.global.exception.SuccessCode.SUCCESS_GET_ALL_JOINED_AUCTION;
import static me.kimgunwoo.auctionseats.global.exception.SuccessCode.SUCCESS_GET_AUCTION_INFO;

@RequiredArgsConstructor
@RequestMapping("/api/v1/auctions")
@RestController
public class AuctionController {
    private final AuctionService auctionService;

    @GetMapping("/{auctionId}")
    public ResponseEntity<ApiResponse<AuctionDetailResponse>> getAuctionInfo(@PathVariable Long auctionId) {
        var response = auctionService.getAuctionInfo(auctionId);
        return ResponseEntity.status(SUCCESS_GET_AUCTION_INFO.getHttpStatus())
                .body(
                        ApiResponse.of(
                                SUCCESS_GET_AUCTION_INFO.getCode(),
                                SUCCESS_GET_AUCTION_INFO.getMessage(),
                                response
                        )
                );
    }

    @GetMapping
    public ResponseEntity<ApiResponse<Page<AuctionInfoResponse>>> getMyJoinedAuctions(
            @CurrentUser User loginUser,
            @PageableDefault(size = 15, sort = "startDateTime", direction = Sort.Direction.DESC)
            Pageable pageable
    ) {
        var responses = auctionService.getMyJoinedAuctions(loginUser, pageable);
        return ResponseEntity.status(SUCCESS_GET_ALL_JOINED_AUCTION.getHttpStatus())
                .body(
                        ApiResponse.of(
                                SUCCESS_GET_ALL_JOINED_AUCTION.getCode(),
                                SUCCESS_GET_ALL_JOINED_AUCTION.getMessage(),
                                responses
                        )
                );
    }
}