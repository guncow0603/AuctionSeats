package me.kimgunwoo.auctionseats.domain.auction.dto.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import me.kimgunwoo.auctionseats.domain.auction.entity.Auction;

@Getter
@RequiredArgsConstructor
public class AuctionDetailResponse {
    private final Long id;
    //입찰가
    private final Long bidPrice;
    //시작가
    private final Long startPrice;
    //남은시간
    private final Long remainTimeMilli;

    public static AuctionDetailResponse from(Auction entity, Long bidPrice, Long remainTimeMilli) {
        return new AuctionDetailResponse(
                entity.getId(),
                bidPrice,
                entity.getStartPrice(),
                remainTimeMilli
        );
    }
}