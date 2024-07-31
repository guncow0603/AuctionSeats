package me.kimgunwoo.auctionseats.domain.auction.dto.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import me.kimgunwoo.auctionseats.domain.auction.entity.Auction;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class AuctionDetailResponse {
    private final Long id;
    //입찰가
    private final Long bidPrice;
    //시작가
    private final Long startPrice;
    //종료일자
    private final LocalDateTime endDateTime;

    public static AuctionDetailResponse from(Auction entity, Long bidPrice) {
        return new AuctionDetailResponse(
                entity.getId(),
                bidPrice,
                entity.getStartPrice(),
                entity.getEndDateTime()
        );
    }
}