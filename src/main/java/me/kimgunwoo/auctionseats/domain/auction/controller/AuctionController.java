package me.kimgunwoo.auctionseats.domain.auction.controller;

import lombok.RequiredArgsConstructor;
import me.kimgunwoo.auctionseats.domain.auction.dto.response.AuctionInfoResponse;
import me.kimgunwoo.auctionseats.domain.auction.service.AuctionService;
import me.kimgunwoo.auctionseats.global.response.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static me.kimgunwoo.auctionseats.global.exception.SuccessCode.SUCCESS_GET_AUCTION_INFO;

@RequiredArgsConstructor
@RequestMapping("/api/v1/auctions")
@RestController
public class AuctionController {
    private final AuctionService auctionService;

    @GetMapping("/{auctionId}")
    public ResponseEntity<ApiResponse<AuctionInfoResponse>> getAuctionInfo(@PathVariable Long auctionId) {
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
}