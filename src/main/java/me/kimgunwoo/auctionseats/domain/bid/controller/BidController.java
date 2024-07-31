package me.kimgunwoo.auctionseats.domain.bid.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import me.kimgunwoo.auctionseats.domain.bid.dto.request.BidRequest;
import me.kimgunwoo.auctionseats.domain.bid.dto.response.BidInfoResponse;
import me.kimgunwoo.auctionseats.domain.bid.redis.RedisPublisher;
import me.kimgunwoo.auctionseats.domain.bid.service.BidService;
import me.kimgunwoo.auctionseats.domain.user.entity.User;
import me.kimgunwoo.auctionseats.global.annotaion.CurrentUser;
import me.kimgunwoo.auctionseats.global.dto.EmptyObject;
import me.kimgunwoo.auctionseats.global.response.ApiResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import static me.kimgunwoo.auctionseats.domain.bid.constant.BidConstant.AUCTION_SSE_PREFIX;
import static me.kimgunwoo.auctionseats.global.exception.SuccessCode.SUCCESS_BID;
import static me.kimgunwoo.auctionseats.global.exception.SuccessCode.SUCCESS_GET_ALL_BID;

@RequiredArgsConstructor
@RequestMapping("/api/v1/auctions/{auctionId}/bids")
@RestController
public class BidController {
    private final BidService bidService;
    private final RedisPublisher redisPublisher;
    /*입찰하기*/
    @PostMapping
    public ResponseEntity<ApiResponse<EmptyObject>> bid(
            @PathVariable Long auctionId,
            @Valid @RequestBody BidRequest bidRequest,
            @CurrentUser User loginUser
    ) {
        bidService.bid(auctionId, bidRequest, loginUser);

        //입찰 갱신 sse
        redisPublisher.publish(AUCTION_SSE_PREFIX + auctionId, bidRequest.getPrice());
        return ResponseEntity.status(SUCCESS_BID.getHttpStatus())
                .body(ApiResponse.of(SUCCESS_BID.getCode(), SUCCESS_BID.getMessage()));
    }
    @GetMapping
    public ResponseEntity<ApiResponse<Page<BidInfoResponse>>> getMyBids(
            @PathVariable Long auctionId,
            @CurrentUser User loginUser,
            @PageableDefault(size = 15, sort = "createdAt", direction = Sort.Direction.DESC)
            Pageable pageable
    ) {
        var responses = bidService.getMyBids(auctionId, loginUser, pageable);
        return ResponseEntity.status(SUCCESS_GET_ALL_BID.getHttpStatus())
                .body(
                        ApiResponse.of(
                                SUCCESS_GET_ALL_BID.getCode(),
                                SUCCESS_GET_ALL_BID.getMessage(),
                                responses
                        )
                );
    }
    @GetMapping(value = "/sse", produces = "text/event-stream")
    public SseEmitter subscribe(@PathVariable Long auctionId) {
        return bidService.subscribe(auctionId);
    }
}