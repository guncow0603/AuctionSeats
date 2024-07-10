package me.kimgunwoo.auctionseats.domain.shows_schedule_seat.service;

import lombok.RequiredArgsConstructor;
import me.kimgunwoo.auctionseats.domain.shows_schedule_seat.entity.SellType;
import me.kimgunwoo.auctionseats.domain.shows_schedule_seat.entity.ShowsScheduleSeat;
import me.kimgunwoo.auctionseats.domain.shows_schedule_seat.repository.ShowsScheduleSeatRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ShowsScheduleSeatServiceImpl implements ShowsScheduleSeatService {
    private final ShowsScheduleSeatRepository showsScheduleSeatRepository;
    // 공연 회차 별 좌석 저장
    @Override
    public void saveAllShowsScheduleSeat(List<ShowsScheduleSeat> showsScheduleSeatList) {
        showsScheduleSeatRepository.saveAll(showsScheduleSeatList);
    }
    // 공연 회차별 타입 좌석 조회
    @Override
    public List<ShowsScheduleSeat> findAllByScheduleIdAndSellType(Long scheduleId, SellType sellType) {
        return showsScheduleSeatRepository.findAllByScheduleIdAndSellType(scheduleId, sellType);
        }
}
