package me.kimgunwoo.auctionseats.domain.shows_schedule_seat.repository;

import me.kimgunwoo.auctionseats.domain.shows_schedule_seat.entity.SellType;
import me.kimgunwoo.auctionseats.domain.shows_schedule_seat.entity.ShowsScheduleSeat;
import me.kimgunwoo.auctionseats.domain.shows_schedule_seat.entity.ShowsScheduleSeatID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShowsScheduleSeatRepository extends JpaRepository<ShowsScheduleSeat, ShowsScheduleSeatID> {
    // 공연 회차별 타입 좌석 조회
    List<ShowsScheduleSeat> findAllByScheduleIdAndSellType(Long scheduleId, SellType sellType);
}
