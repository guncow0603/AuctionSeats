package me.kimgunwoo.auctionseats.domain.bid.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import me.kimgunwoo.auctionseats.domain.bid.dto.request.BidRequest;
import me.kimgunwoo.auctionseats.domain.bid.service.BidService;
import me.kimgunwoo.auctionseats.domain.user.entity.User;
import me.kimgunwoo.auctionseats.global.annotaion.CurrentUser;
import me.kimgunwoo.auctionseats.global.dto.EmptyObject;
import me.kimgunwoo.auctionseats.global.response.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static me.kimgunwoo.auctionseats.global.exception.SuccessCode.SUCCESS_BID;

@RequiredArgsConstructor
@RequestMapping("/api/v1/auctions/{auctionId}/bids")
@RestController
public class BidController {
    private final BidService bidService;

    /*입찰하기*/
    @PostMapping
    public ResponseEntity<ApiResponse<EmptyObject>> bid(
            @PathVariable Long auctionId,
            @Valid @RequestBody BidRequest bidRequest,
            @CurrentUser User loginUser
    ) {
        bidService.bid(auctionId, bidRequest, loginUser);
        return ResponseEntity.status(SUCCESS_BID.getHttpStatus())
                .body(ApiResponse.of(SUCCESS_BID.getCode(), SUCCESS_BID.getMessage()));
    }
}