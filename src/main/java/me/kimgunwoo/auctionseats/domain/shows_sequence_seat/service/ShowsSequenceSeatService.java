package me.kimgunwoo.auctionseats.domain.shows_sequence_seat.service;

import me.kimgunwoo.auctionseats.domain.shows_sequence_seat.entity.ShowsSequenceSeat;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ShowsSequenceSeatService {
    // 공연 회차 별 좌석 저장
    void saveAllShowsSequenceSeat(List<ShowsSequenceSeat> showsSequenceSeatList);

    // 회차별 공연 조회
    List<ShowsSequenceSeat> findAllBySequenceId(Long sequenceId);
}
