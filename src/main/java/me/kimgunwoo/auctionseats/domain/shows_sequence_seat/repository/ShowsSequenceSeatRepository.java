package me.kimgunwoo.auctionseats.domain.shows_sequence_seat.repository;

import me.kimgunwoo.auctionseats.domain.shows_sequence_seat.entity.ShowsSequenceSeat;
import me.kimgunwoo.auctionseats.domain.shows_sequence_seat.entity.ShowsSequenceSeatID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShowsSequenceSeatRepository extends JpaRepository<ShowsSequenceSeat, ShowsSequenceSeatID> {
}
