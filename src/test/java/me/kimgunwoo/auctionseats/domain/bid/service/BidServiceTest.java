package me.kimgunwoo.auctionseats.domain.bid.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;

import java.time.LocalDateTime;
import java.util.Optional;

import me.kimgunwoo.auctionseats.domain.auction.entity.Auction;
import me.kimgunwoo.auctionseats.domain.auction.repository.AuctionRepository;
import me.kimgunwoo.auctionseats.domain.bid.dto.request.BidRequest;
import me.kimgunwoo.auctionseats.domain.bid.entity.Bid;
import me.kimgunwoo.auctionseats.domain.bid.repository.BidRepository;
import me.kimgunwoo.auctionseats.domain.user.entity.User;
import me.kimgunwoo.auctionseats.domain.user.service.PointService;
import me.kimgunwoo.auctionseats.domain.user.service.UserService;
import me.kimgunwoo.auctionseats.domain.user.util.UserUtil;
import me.kimgunwoo.auctionseats.global.exception.ApiException;
import me.kimgunwoo.auctionseats.global.exception.ErrorCode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

@DisplayName("[입찰] 서비스 테스트")
@ExtendWith(MockitoExtension.class)
class BidServiceTest {
    @InjectMocks
    private BidServiceImpl sut;

    @Mock
    private AuctionRepository auctionRepository;

    @Mock
    private BidRepository bidRepository;

    @Mock
    private BidRedisService bidRedisService;

    @Mock
    private UserService userService;

    @Mock
    private PointService pointService;

    //로그인한 유저가
    //특정 경매에 입찰금액을 적고 입찰하기 버튼을 눌렀을때.
    //해당 경매에 대한 입찰 기록을 저장하고
    //경매의 현재가 최신화를 해줘야함.
    @Test
    void 입찰하기_정상_테스트() throws Exception {
        //Given
        Long auctionId = 1L;
        Auction auction = getAuction(auctionId);

        BidRequest bidRequest = new BidRequest(10_0000L);

        User bidder = UserUtil.createUser();
        ReflectionTestUtils.setField(bidder, "point", 10_0000L);

        given(auctionRepository.findById(auctionId))
                .willReturn(Optional.of(auction));

        given(userService.findByUserId(any()))
                .willReturn(bidder);
        System.out.println(bidder.getPoint());

        given(bidRedisService.getBidPrice(any()))
                .willReturn(Optional.of(1000L));

        //When
        sut.bid(auctionId, bidRequest, bidder);

        //Then
        then(bidRepository).should().save(any(Bid.class));
        then(bidRedisService).should().setBidPrice(any(), any());
        then(pointService).should().usePoint(bidder, bidRequest.price());
    }

    @Test
    void 입찰하기_하회입찰_실패_테스트() throws Exception {
        //Given
        Long auctionId = 1L;
        Auction auction = getAuction(auctionId);
        BidRequest bidRequest = new BidRequest(10_000L);

        User bidder = UserUtil.createUser();
        ReflectionTestUtils.setField(bidder, "point", 10_0000L);

        given(auctionRepository.findById(auctionId))
                .willReturn(Optional.of(auction));

        given(bidRedisService.getBidPrice(any()))
                .willReturn(Optional.of(1000_000L));

        //When & Then
        assertThatThrownBy(() -> sut.bid(auctionId, bidRequest, bidder))
                .isInstanceOf(ApiException.class)
                .hasMessage(ErrorCode.BAD_REQUEST_BID.getMessage());
    }

    @Test
    void 입찰하기_경매종료_테스트() throws Exception {
        //Given
        Long auctionId = 1L;
        Auction auction = getAuction(auctionId);
        ReflectionTestUtils.setField(auction, "isEnded", true);

        BidRequest bidRequest = new BidRequest(10_0000L);

        given(auctionRepository.findById(auctionId))
                .willReturn(Optional.of(auction));

        //When & Then
        assertThatThrownBy(() -> sut.bid(auctionId, bidRequest, UserUtil.createUser()))
                .isInstanceOf(ApiException.class)
                .hasMessage(ErrorCode.ENDED_AUCTION.getMessage());
    }

    private static Auction getAuction(Long auctionId) {
        Auction auction = Auction.builder()
                .startDateTime(LocalDateTime.now())
                .endDateTime(LocalDateTime.now().plusDays(7))
                .startPrice(1000L)
                .build();
        ReflectionTestUtils.setField(auction, "id", auctionId);
        return auction;
    }

}