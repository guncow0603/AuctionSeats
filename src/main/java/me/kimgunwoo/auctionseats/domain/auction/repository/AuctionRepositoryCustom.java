package me.kimgunwoo.auctionseats.domain.auction.repository;

import me.kimgunwoo.auctionseats.domain.auction.dto.response.AuctionInfoResponse;
import me.kimgunwoo.auctionseats.domain.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AuctionRepositoryCustom {
    Page<AuctionInfoResponse> getJoinedMyAuctions(User user, Pageable pageable);
}