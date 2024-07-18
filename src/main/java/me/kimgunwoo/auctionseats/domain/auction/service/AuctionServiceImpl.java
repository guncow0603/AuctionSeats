package me.kimgunwoo.auctionseats.domain.auction.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.kimgunwoo.auctionseats.domain.auction.dto.request.AuctionCreateRequest;
import me.kimgunwoo.auctionseats.domain.auction.dto.response.AuctionInfoResponse;
import me.kimgunwoo.auctionseats.domain.auction.entity.Auction;
import me.kimgunwoo.auctionseats.domain.auction.repository.AuctionRepository;
import me.kimgunwoo.auctionseats.domain.bid.service.BidRedisService;
import me.kimgunwoo.auctionseats.domain.bid.service.BidService;
import me.kimgunwoo.auctionseats.domain.grade.entity.ZoneGrade;
import me.kimgunwoo.auctionseats.domain.grade.repository.ZoneGradeRepository;
import me.kimgunwoo.auctionseats.domain.schedule.entity.Schedule;
import me.kimgunwoo.auctionseats.domain.schedule.repository.ScheduleRepository;
import me.kimgunwoo.auctionseats.domain.user.entity.User;
import me.kimgunwoo.auctionseats.global.exception.ApiException;
import me.kimgunwoo.auctionseats.global.exception.ErrorCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;

@Slf4j(topic = "경매 서비스")
@RequiredArgsConstructor
@Service
public class AuctionServiceImpl implements AuctionService {
    private final AuctionRepository auctionRepository;
    private final ScheduleRepository scheduleRepository;
    private final ZoneGradeRepository zoneGradeRepository;
    private final BidService bidService;
    private final BidRedisService bidRedisService;
    @Override
    @Transactional
    public void createAuction(Long scheduleId, Long zoneGradeId, AuctionCreateRequest request) {
        Schedule schedule = scheduleRepository.getReferenceById(scheduleId);
        ZoneGrade zoneGrade = zoneGradeRepository.getReferenceById(zoneGradeId);

        Auction auction = request.toEntity(schedule, zoneGrade);
        auctionRepository.save(auction);
        bidRedisService.saveWithExpire(auction, genRemainSeconds(auction));
    }

    @Override
    @Transactional
    public void endAuction(Long auctionId) {
        Auction auction = getAuction(auctionId);
        auction.ended();

        User bidWinner = getBidWinner(auction);

        log.info("예매 성공! id: {]", auctionId);
    }

    @Override
    public AuctionInfoResponse getAuctionInfo(Long auctionId) {
        Auction auction = getAuction(auctionId);
        Long remainTimeMilli = bidRedisService.getRemainTimeMilli(auctionId);
        return AuctionInfoResponse.from(auction, remainTimeMilli);
    }

    @Override
    public Auction getAuction(Long auctionId) {
        return auctionRepository.findById(auctionId)
                .orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND_AUCTION));
    }

    public User getBidWinner(Auction auction) {
        return bidService.getCurrentBid(auction)
                .orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND_WIN_BID))
                .getUser();
    }

    private long genRemainSeconds(Auction auction) {
        Duration duration = Duration.between(auction.getStartDateTime(), auction.getEndDateTime());
        return duration.getSeconds();
    }
}