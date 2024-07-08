package me.kimgunwoo.auctionseats.domain.shows_sequence_seat.repository;

import me.kimgunwoo.auctionseats.domain.shows_sequence_seat.entity.SellType;
import me.kimgunwoo.auctionseats.domain.shows_sequence_seat.entity.ShowsSequenceSeat;
import me.kimgunwoo.auctionseats.domain.shows_sequence_seat.entity.ShowsSequenceSeatID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShowsSequenceSeatRepository extends JpaRepository<ShowsSequenceSeat, ShowsSequenceSeatID> {
    // 공연 회차별 타입 좌석 조회
    List<ShowsSequenceSeat> findAllBySequenceIdAndSellType(Long sequenceId, SellType sellType);
}
