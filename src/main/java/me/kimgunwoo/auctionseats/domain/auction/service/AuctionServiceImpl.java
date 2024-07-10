package me.kimgunwoo.auctionseats.domain.auction.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.kimgunwoo.auctionseats.domain.auction.entity.Auction;
import me.kimgunwoo.auctionseats.domain.auction.repository.AuctionRepository;
import me.kimgunwoo.auctionseats.domain.bid.service.BidRedisService;
import me.kimgunwoo.auctionseats.domain.bid.service.BidService;
import me.kimgunwoo.auctionseats.domain.reservation.service.ReservationService;
import me.kimgunwoo.auctionseats.domain.shows_schedule_seat.entity.ShowsScheduleSeat;
import me.kimgunwoo.auctionseats.domain.user.entity.User;
import me.kimgunwoo.auctionseats.global.exception.ApiException;
import me.kimgunwoo.auctionseats.global.exception.ErrorCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.util.List;

@Slf4j(topic = "경매 서비스")
@RequiredArgsConstructor
@Service
public class AuctionServiceImpl implements AuctionService {
    private final AuctionRepository auctionRepository;
    private final BidService bidService;
    private final BidRedisService bidRedisService;
    private final ReservationService reservationService;
    
    @Override
    @Transactional
    public void createAuction(List<ShowsScheduleSeat> scheduleSeats) {
        List<Auction> auctions = scheduleSeats.stream().map(scheduleSeat ->
                        Auction.builder()
                                .startPrice(scheduleSeat.getPrice())
                                .startDateTime(scheduleSeat.getCreatedAt())
                                .endDateTime(scheduleSeat.getSchedule().getStartDateTime())
                                .scheduleSeat(scheduleSeat)
                                .build()
                )
                .toList();
        auctionRepository.saveAll(auctions);

        auctions.forEach(auction -> {
                    log.debug("success create auction! id: {}", auction.getId());
                    bidRedisService.saveWithExpire(auction, genRemainSeconds(auction));
                }
        );
    }
    
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

    private long genRemainSeconds(Auction auction) {
        Duration duration = Duration.between(auction.getStartDateTime(), auction.getEndDateTime());
        return duration.getSeconds();
    }
}