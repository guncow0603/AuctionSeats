package me.kimgunwoo.auctionseats.domain.auction.repository;

import me.kimgunwoo.auctionseats.domain.auction.entity.Bid;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BidRepository extends JpaRepository<Bid, Long> {
}
