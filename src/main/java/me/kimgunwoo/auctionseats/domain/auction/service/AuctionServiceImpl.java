package me.kimgunwoo.auctionseats.domain.auction.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.kimgunwoo.auctionseats.domain.auction.entity.Auction;
import me.kimgunwoo.auctionseats.domain.auction.repository.AuctionRepository;
import me.kimgunwoo.auctionseats.domain.bid.service.BidService;
import me.kimgunwoo.auctionseats.domain.reservation.service.ReservationService;
import me.kimgunwoo.auctionseats.domain.shows_schedule_seat.entity.ShowsScheduleSeat;
import me.kimgunwoo.auctionseats.domain.user.entity.User;
import me.kimgunwoo.auctionseats.global.exception.ApiException;
import me.kimgunwoo.auctionseats.global.exception.ErrorCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j(topic = "경매 서비스")
@RequiredArgsConstructor
@Service
public class AuctionServiceImpl implements AuctionService {
    private final AuctionRepository auctionRepository;
    private final BidService bidService;
    private final ReservationService reservationService;

    // TODO: 1/10/24  추후 회차좌석과 이벤트기반으로 의존성 분리하기
    @Override
    @Transactional
    public void createAuction(List<ShowsScheduleSeat> scheduleSeats) {
    }

    // TODO: 1/10/24  추후 예매와 이벤트기반으로 의존성 분리하기
    @Override
    @Transactional
    public void endAuction(Long auctionId) {
        Auction auction = getAuction(auctionId);
        auction.ended();

        User bidWinner = getBidWinner(auction);

        log.info("예매 성공! id: {]", auctionId);
        reservationService.reserve(auction, bidWinner);
    }

    public User getBidWinner(Auction auction) {
        return bidService.getCurrentBid(auction)
                .orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND_WIN_BID))
                .getUser();
    }

    public Auction getAuction(Long auctionId) {
        return auctionRepository.findById(auctionId)
                .orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND_AUCTION));
    }
}
