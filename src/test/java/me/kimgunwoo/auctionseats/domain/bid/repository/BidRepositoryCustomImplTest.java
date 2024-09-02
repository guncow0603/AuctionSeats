package me.kimgunwoo.auctionseats.domain.bid.repository;

import me.kimgunwoo.auctionseats.domain.auction.entity.Auction;
import me.kimgunwoo.auctionseats.domain.auction.repository.AuctionRepository;
import me.kimgunwoo.auctionseats.domain.bid.dto.response.BidInfoResponse;
import me.kimgunwoo.auctionseats.domain.bid.entity.Bid;
import me.kimgunwoo.auctionseats.domain.user.entity.User;
import me.kimgunwoo.auctionseats.domain.user.repository.UserRepository;
import me.kimgunwoo.auctionseats.domain.user.util.UserUtil;
import me.kimgunwoo.auctionseats.global.config.AuditingConfig;
import me.kimgunwoo.auctionseats.global.config.QueryDslConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Import({QueryDslConfig.class, AuditingConfig.class})
class BidRepositoryCustomImplTest {
    @Autowired
    BidRepository bidRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    AuctionRepository auctionRepository;

    @BeforeEach
    void setUp() {
        userRepository.save(UserUtil.getUser());
        auctionRepository.save(getAuction(1L));

        Bid bid1 = getBid(1L, 1000L, 1);
        bidRepository.save(bid1);

        Bid bid2 = getBid(2L, 1000L, 2);
        bidRepository.save(bid2);

        Bid bid3 = getBid(3L, 1000L, 3);
        bidRepository.save(bid3);

        Bid bid4 = getBid(4L, 1000L, 4);
        bidRepository.save(bid4);
    }

    private static Bid getBid(Long bidId, Long price, int plusDays) {
        Bid bid = Bid.builder()
                .price(price)
                .user(UserUtil.getUser())
                .auction(getAuction(1L))
                .build();

        ReflectionTestUtils.setField(bid, "id", bidId);
        ReflectionTestUtils.setField(bid, "createdAt", LocalDateTime.now().plusDays(plusDays));
        return bid;
    }

    @Test
    void 입찰_내역_조회시_가장_최근_입찰일순으로_정렬되어_조회한다() {
        //given
        Long auctionId = 1L;
        Pageable pageable = PageRequest.of(0, 10, Sort.Direction.DESC, "createdAt");
        User user = UserUtil.getUser();

        //when
        Page<BidInfoResponse> responses = bidRepository.getMyBids(auctionId, user, pageable);

        //then
        assertThat(responses.getContent()).hasSize(4);
        assertThat(responses.getContent().get(0).id()).isEqualTo(4L);
        assertThat(responses.getContent().get(1).id()).isEqualTo(3L);
        assertThat(responses.getContent().get(2).id()).isEqualTo(2L);
        assertThat(responses.getContent().get(3).id()).isEqualTo(1L);
    }


    private static Auction getAuction(Long auctionId) {
        Auction auction = Auction.builder()
                .seatNumber(1)
                .startPrice(1000L)
                .endDateTime(LocalDateTime.now())
                .startDateTime(LocalDateTime.now())
                .build();

        ReflectionTestUtils.setField(auction, "id", auctionId);
        return auction;
    }
}