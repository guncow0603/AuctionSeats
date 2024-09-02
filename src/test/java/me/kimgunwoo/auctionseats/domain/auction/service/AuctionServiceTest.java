package me.kimgunwoo.auctionseats.domain.auction.service;

import me.kimgunwoo.auctionseats.domain.auction.dto.request.AuctionCreateRequest;
import me.kimgunwoo.auctionseats.domain.auction.entity.Auction;
import me.kimgunwoo.auctionseats.domain.auction.repository.AuctionRepository;
import me.kimgunwoo.auctionseats.domain.bid.entity.Bid;
import me.kimgunwoo.auctionseats.domain.bid.repository.BidRepository;
import me.kimgunwoo.auctionseats.domain.bid.service.BidRedisService;
import me.kimgunwoo.auctionseats.domain.bid.service.BidServiceImpl;
import me.kimgunwoo.auctionseats.domain.grade.entity.Grade;
import me.kimgunwoo.auctionseats.domain.grade.entity.ZoneGrade;
import me.kimgunwoo.auctionseats.domain.grade.repository.ZoneGradeRepository;
import me.kimgunwoo.auctionseats.domain.reservation.service.ReservationService;
import me.kimgunwoo.auctionseats.domain.schedule.entity.Schedule;
import me.kimgunwoo.auctionseats.domain.schedule.repository.ScheduleRepository;
import me.kimgunwoo.auctionseats.domain.user.entity.User;
import me.kimgunwoo.auctionseats.domain.user.util.UserUtil;
import me.kimgunwoo.auctionseats.global.exception.ApiException;
import me.kimgunwoo.auctionseats.global.exception.ErrorCode;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@DisplayName("[경매] 테스트")
@ExtendWith(MockitoExtension.class)
class AuctionServiceTest {
    @InjectMocks
    private AuctionServiceImpl sut;

    @Mock
    private BidRepository bidRepository;

    @Mock
    private AuctionRepository auctionRepository;

    @Mock
    private ScheduleRepository scheduleRepository;

    @Mock
    private ZoneGradeRepository zoneGradeRepository;

    @Mock
    private ReservationService reservationService;

    @Mock
    private BidServiceImpl bidService;
    @Mock
    private BidRedisService bidRedisService;

    @Nested
    class 경매_등록_테스트 {
        @Test
        void 정상() {
            AuctionCreateRequest request = new AuctionCreateRequest(100);
            Grade grade = Grade.builder().auctionPrice(1000L).build();
            Schedule schedule = Schedule.builder().startDateTime(LocalDateTime.now()).build();
            ZoneGrade zoneGrade = ZoneGrade.builder().grade(grade).build();

            given(scheduleRepository.getReferenceById(any())).willReturn(schedule);
            given(zoneGradeRepository.getReferenceById(any())).willReturn(zoneGrade);

            //when
            sut.createAuction(1L, 1L, request);

            //then
            then(auctionRepository).should().save(any(Auction.class));
            then(bidRedisService).should().saveWithExpire(any(Auction.class));
        }
    }

    @Nested
    class 경매_종료_테스트 {
        @Test
        void 정상() {
            Long auctionId = 1L;
            Long bidId = 1L;
            Auction auction = getAuction(auctionId);
            Bid bid = getBid(auction, bidId);

            given(auctionRepository.findById(auctionId))
                    .willReturn(Optional.of(auction));

            given(bidRepository.findTopByAuctionOrderByIdDesc(auction))
                    .willReturn(Optional.of(bid));

            // When
            sut.endAuction(auctionId);

            // Then
            then(bidRepository).should().findTopByAuctionOrderByIdDesc(auction);
            then(reservationService).should().reserve(bid, auction);
        }

        @Test
        void 종료된_경매가_없는경우_실패() {
            //Given
            Long auctionId = 1L;
            given(auctionRepository.findById(auctionId))
                    .willReturn(Optional.empty());

            //when && Then
            Assertions.assertThatThrownBy(() -> sut.endAuction(auctionId))
                    .isInstanceOf(ApiException.class)
                    .hasMessage(ErrorCode.NOT_FOUND_AUCTION.getMessage());

        }
    }

    private static Auction getAuction(Long auctionId) {
        Auction auction = Auction.builder()
                .seatNumber(1)
                .build();

        ReflectionTestUtils.setField(auction, "id", auctionId);
        return auction;
    }

    private static Bid getBid(Auction auction, Long bidId) {
        User bidWinner = UserUtil.createUser();
        Bid bid = Bid.builder()
                .auction(auction)
                .user(bidWinner)
                .build();

        ReflectionTestUtils.setField(bid, "id", bidId);
        return bid;
    }
}
