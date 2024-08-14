package me.kimgunwoo.auctionseats.domain.bid.service;

import me.kimgunwoo.auctionseats.domain.auction.entity.Auction;
import me.kimgunwoo.auctionseats.domain.bid.dto.request.BidRequest;
import me.kimgunwoo.auctionseats.domain.bid.dto.response.BidInfoResponse;
import me.kimgunwoo.auctionseats.domain.bid.dto.response.BidInfoWithNickname;
import me.kimgunwoo.auctionseats.domain.bid.entity.Bid;
import me.kimgunwoo.auctionseats.domain.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.List;
import java.util.Optional;


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

    /**
     * 경매의 최근입찰 조회
     * @param auction - 경매
     * @return 최근 입찰
     */
    Optional<Bid> getCurrentBid(Auction auction);

    SseEmitter subscribe(Long auctionId);

    /**
     * 내 입찰 내역 조회 기능
     *
     * @param auctionId - 경매 식별자 ID
     * @param loginUser - 로그인 인증 유저
     * @param pageable  - 페이징 조건 객체
     * @return - 페이징된 입찰내역
     */
    Page<BidInfoResponse> getMyBids(Long auctionId, User loginUser, Pageable pageable);

    /**
     * 경매 최고 입찰가 조회
     * @param auction
     * @return - 해당 경매의 최고 입찰가
     */
    Optional<Long> getMaxBidPrice(Auction auction);

    /**
     * 유저가 해당 경매의 최상위 입찰자인지 검사
     * @param userId
     * @param auctionId
     * @return 맞다면 true, 아니라면 false
     */
    boolean isUserBidHighest(Long userId, Long auctionId);

    /**
     * 해당 경매의 최근 입찰 내역 n 개의 유저 닉네임과 가격을 조회한다.
     * @param auctionId 경매 id
     * @param limit 조회할 양
     * @return List<BidInfoWithUserResponse>
     */
    List<BidInfoWithNickname> getLastBidsNicknameAndPrice(Long auctionId, Long limit);
}