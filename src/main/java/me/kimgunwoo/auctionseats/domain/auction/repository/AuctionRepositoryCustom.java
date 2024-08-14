package me.kimgunwoo.auctionseats.domain.auction.repository;

import me.kimgunwoo.auctionseats.domain.auction.dto.response.AuctionInfoResponse;
import me.kimgunwoo.auctionseats.domain.auction.entity.Auction;
import me.kimgunwoo.auctionseats.domain.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface AuctionRepositoryCustom {
    Page<AuctionInfoResponse> getJoinedMyAuctions(User user, Pageable pageable);

    boolean exists(Auction auction);

    Optional<Auction> findBySeatInfo(Long scheduleId, Long zoneGradeId, Integer seatNumber);
}
