package me.kimgunwoo.auctionseats.domain.auction.service;

import me.kimgunwoo.auctionseats.domain.auction.dto.request.BidRequest;
import me.kimgunwoo.auctionseats.domain.user.entity.User;

public interface BidService {
    /**
     * 입찰하기 기능
     * 경매에 입찰하는 기능
     *
     * @param auctionId  - 경매 식별자 ID
     * @param bidRequest - 입찰 요청 DTO
     * @param loginUser - 인증된 로그인 유저
     */
    void bid(Long auctionId, BidRequest bidRequest, User loginUser);
}
