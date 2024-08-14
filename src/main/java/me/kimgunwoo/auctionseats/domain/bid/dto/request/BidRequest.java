package me.kimgunwoo.auctionseats.domain.bid.dto.request;

import jakarta.validation.constraints.NotNull;
import me.kimgunwoo.auctionseats.domain.auction.entity.Auction;
import me.kimgunwoo.auctionseats.domain.bid.entity.Bid;
import me.kimgunwoo.auctionseats.domain.user.entity.User;

public record BidRequest(
        @NotNull(message = "입찰가를 입력해주세요.")
        Long price
) {
    public Bid toEntity(User user, Auction auction) {
        return Bid.builder()
                .price(price)
                .user(user)
                .auction(auction)
                .build();
    }
}