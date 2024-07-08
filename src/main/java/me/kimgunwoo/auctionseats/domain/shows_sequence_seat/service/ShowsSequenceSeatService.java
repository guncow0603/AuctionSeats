package me.kimgunwoo.auctionseats.domain.shows_sequence_seat.service;

import me.kimgunwoo.auctionseats.domain.shows_sequence_seat.entity.SellType;
import me.kimgunwoo.auctionseats.domain.shows_sequence_seat.entity.ShowsSequenceSeat;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ShowsSequenceSeatService {
    // 공연 회차 별 좌석 저장
    void saveAllShowsSequenceSeat(List<ShowsSequenceSeat> showsSequenceSeatList);

   // 공연 회차별 타입 좌석 조회
    List<ShowsSequenceSeat> findAllBySequenceIdAndSellType(Long sequenceId, SellType sellType);
}
