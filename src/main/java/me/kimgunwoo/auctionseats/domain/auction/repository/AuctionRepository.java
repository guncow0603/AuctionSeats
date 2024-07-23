package me.kimgunwoo.auctionseats.domain.auction.repository;

import me.kimgunwoo.auctionseats.domain.auction.entity.Auction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuctionRepository extends JpaRepository<Auction, Long>, AuctionRepositoryCustom {
}
