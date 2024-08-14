package me.kimgunwoo.auctionseats.domain.auction.service;

import me.kimgunwoo.auctionseats.domain.auction.dto.request.AuctionCreateRequest;
import me.kimgunwoo.auctionseats.domain.auction.dto.response.AuctionDetailResponse;
import me.kimgunwoo.auctionseats.domain.auction.dto.response.AuctionInfoResponse;
import me.kimgunwoo.auctionseats.domain.auction.entity.Auction;
import me.kimgunwoo.auctionseats.domain.auction.repository.AuctionRepository;
import me.kimgunwoo.auctionseats.domain.bid.constant.BidStatus;
import me.kimgunwoo.auctionseats.domain.bid.entity.Bid;
import me.kimgunwoo.auctionseats.domain.bid.repository.BidRepository;
import me.kimgunwoo.auctionseats.domain.bid.service.BidRedisService;
import me.kimgunwoo.auctionseats.domain.grade.entity.ZoneGrade;
import me.kimgunwoo.auctionseats.domain.grade.repository.ZoneGradeRepository;
import me.kimgunwoo.auctionseats.domain.reservation.service.ReservationService;
import me.kimgunwoo.auctionseats.domain.schedule.entity.Schedule;
import me.kimgunwoo.auctionseats.domain.schedule.repository.ScheduleRepository;
import me.kimgunwoo.auctionseats.domain.user.entity.User;
import me.kimgunwoo.auctionseats.global.exception.ApiException;
import me.kimgunwoo.auctionseats.global.exception.ErrorCode;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

@Slf4j(topic = "경매 서비스")
@RequiredArgsConstructor
@Service
public class AuctionServiceImpl implements AuctionService {
    private final AuctionRepository auctionRepository;
    private final ScheduleRepository scheduleRepository;
    private final ZoneGradeRepository zoneGradeRepository;
    private final BidRepository bidRepository;
    private final BidRedisService bidRedisService;
    private final ReservationService reservationService;

    @Override
    @Transactional
    public void createAuction(Long scheduleId, Long zoneGradeId, AuctionCreateRequest request) {
        Schedule schedule = scheduleRepository.getReferenceById(scheduleId);
        ZoneGrade zoneGrade = zoneGradeRepository.getReferenceById(zoneGradeId);

        Auction auction = request.toEntity(schedule, zoneGrade);
        if (auctionRepository.exists(auction)) {
            throw new ApiException(ErrorCode.ACCESS_DENIED);
        }
        auctionRepository.save(auction);
        bidRedisService.saveWithExpire(auction);
    }

    @Override
    @Transactional
    public void endAuction(Long auctionId) {
        Auction auction = getAuction(auctionId);
        auction.ended();

        //경매 종료 시 입찰자가 없으면 예매 x
        Optional<Bid> bidOptional = bidRepository.findTopByAuctionOrderByIdDesc(auction);
        bidOptional.ifPresent(bid -> {
            bid.updateStatus(BidStatus.SUCCESS);
            reservationService.reserve(bid, auction);

        });
    }

    @Override
    @Transactional(readOnly = true)
    public AuctionDetailResponse getAuctionInfo(Long auctionId) {
        Auction auction = getAuction(auctionId);
        long bidPrice = bidRedisService.getBidPrice(auctionId)
                .orElseGet(() ->
                        bidRepository.getMaxBidPrice(auction).orElse(auction.getStartPrice())
                );
        return AuctionDetailResponse.from(auction, bidPrice);
    }

    @Override
    public Auction getAuction(Long auctionId) {
        return auctionRepository.findById(auctionId)
                .orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND_AUCTION));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AuctionInfoResponse> getMyJoinedAuctions(User loginUser, Pageable pageable) {
        return auctionRepository.getJoinedMyAuctions(loginUser, pageable);
    }
}