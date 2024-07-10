package me.kimgunwoo.auctionseats.domain.schedule.service;

import me.kimgunwoo.auctionseats.domain.schedule.entity.Schedule;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ScheduleService {
    // 회차 리스트 저장
    void saveAllSchedule(List<Schedule> scheduleList);

    // 회차 저장
    Schedule saveSchedule(Schedule schedule);

    // 회차 탐색
    Schedule findSchedule(Long scheduleId);
}
