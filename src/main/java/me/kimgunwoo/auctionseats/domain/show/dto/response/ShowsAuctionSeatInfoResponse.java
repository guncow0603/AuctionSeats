package me.kimgunwoo.auctionseats.domain.show.dto.response;

import lombok.*;
import me.kimgunwoo.auctionseats.domain.seat.dto.response.AuctionSeatInfoResponse;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class ShowsAuctionSeatInfoResponse {

    List<AuctionSeatInfoResponse> seatInfos;
}