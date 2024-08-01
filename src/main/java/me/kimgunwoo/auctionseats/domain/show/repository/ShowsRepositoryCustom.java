package me.kimgunwoo.auctionseats.domain.show.repository;

import me.kimgunwoo.auctionseats.domain.seat.dto.response.AuctionSeatInfoResponse;
import me.kimgunwoo.auctionseats.domain.seat.dto.response.SeatInfoResponse;

import java.util.List;

public interface ShowsRepositoryCustom {

    List<SeatInfoResponse> findShowsSeatInfo(Long showsId);

    List<AuctionSeatInfoResponse> findShowsAuctionSeatInfo(Long scheduleId, Long showsId);
}