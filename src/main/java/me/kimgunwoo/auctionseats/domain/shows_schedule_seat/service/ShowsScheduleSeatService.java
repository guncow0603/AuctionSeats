package me.kimgunwoo.auctionseats.domain.shows_schedule_seat.service;

import me.kimgunwoo.auctionseats.domain.shows_schedule_seat.entity.SellType;
import me.kimgunwoo.auctionseats.domain.shows_schedule_seat.entity.ShowsScheduleSeat;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ShowsScheduleSeatService {
    // 공연 회차 별 좌석 저장
    void saveAllShowsScheduleSeat(List<ShowsScheduleSeat> showsScheduleSeatList);

   // 공연 회차별 타입 좌석 조회
    List<ShowsScheduleSeat> findAllByScheduleIdAndSellType(Long scheduleId, SellType sellType);
}
