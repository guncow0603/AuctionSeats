package me.kimgunwoo.auctionseats.domain.schedule.service;

import me.kimgunwoo.auctionseats.domain.schedule.entity.Schedule;
import me.kimgunwoo.auctionseats.domain.show.entity.Shows;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;

@Service
public interface ScheduleService {
    // 회차 조회
    Schedule findSchedule(Long ScheduleId);

    //회차 생성
    void createSchedule(Shows shows, LocalTime startTime);

    //회차 요일 및 시작시간 부여
    List<Schedule> distributeSchedule(Shows shows, LocalTime startTime);
}
