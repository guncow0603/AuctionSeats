package me.kimgunwoo.auctionseats.domain.bid.repository;

import me.kimgunwoo.auctionseats.domain.auction.entity.Auction;
import me.kimgunwoo.auctionseats.domain.bid.entity.Bid;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BidRepository extends JpaRepository<Bid, Long>, BidRepositoryCustom {
    Optional<Bid> findTopByAuctionOrderByIdDesc(Auction auction);
}
