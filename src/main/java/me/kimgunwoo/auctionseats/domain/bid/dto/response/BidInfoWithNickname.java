package me.kimgunwoo.auctionseats.domain.bid.dto.response;

import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class BidInfoWithNickname {

    private String nickname;

    private Long price;
}
