package me.kimgunwoo.auctionseats.domain.bid.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import me.kimgunwoo.auctionseats.domain.bid.constant.BidStatus;

import java.time.LocalDateTime;

public record BidInfoResponse(
        Long id,
        Long auctionId,
        Long price,
        String status,
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        LocalDateTime createdAt
) {
    public BidInfoResponse(Long id, Long auctionId, Long price, BidStatus status, LocalDateTime createdAt) {
        this(id, auctionId, price, status.getKo(), createdAt);
    }
}