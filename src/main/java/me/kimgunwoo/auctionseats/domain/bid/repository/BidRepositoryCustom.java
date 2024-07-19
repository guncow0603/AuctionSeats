package me.kimgunwoo.auctionseats.domain.bid.repository;

import me.kimgunwoo.auctionseats.domain.bid.dto.response.BidInfoResponse;
import me.kimgunwoo.auctionseats.domain.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BidRepositoryCustom {
    Page<BidInfoResponse> getMyBids(Long auctionId, User user, Pageable pageable);
}
