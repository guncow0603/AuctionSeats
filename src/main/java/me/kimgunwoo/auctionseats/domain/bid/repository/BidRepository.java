package me.kimgunwoo.auctionseats.domain.bid.repository;

import me.kimgunwoo.auctionseats.domain.bid.entity.Bid;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BidRepository extends JpaRepository<Bid, Long> {
}
