package me.kimgunwoo.auctionseats.domain.auction.dto.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import me.kimgunwoo.auctionseats.domain.auction.entity.Auction;
import me.kimgunwoo.auctionseats.domain.auction.entity.Bid;
import me.kimgunwoo.auctionseats.domain.user.entity.User;

@Getter
public class BidRequest {
    @NotNull(message = "입찰가를 입력해주세요.")
    private final Long price;

    @JsonCreator
    public BidRequest(Long price) {
        this.price = price;
    }

    public Bid toEntity(User user, Auction auction) {
        return Bid.builder()
                .price(price)
                .user(user)
                .auction(auction)
                .build();
    }
}