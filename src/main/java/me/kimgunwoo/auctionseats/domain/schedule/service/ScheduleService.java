package me.kimgunwoo.auctionseats.domain.schedule.service;

import me.kimgunwoo.auctionseats.domain.schedule.dto.response.ScheduleGetResponse;
import me.kimgunwoo.auctionseats.domain.schedule.entity.Schedule;
import me.kimgunwoo.auctionseats.domain.show.entity.Shows;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;

public interface ScheduleService {

    // 회차 조회
    Schedule findSchedule(Long ScheduleId);

    //회차 생성
    void createSchedule(Shows shows, LocalTime startTime);

    //회차 요일 및 시작시간 부여
    List<Schedule> distributeSchedule(Shows shows, LocalTime startTime);

    // 해당 공연에 대한 전 회차 조회
    List<ScheduleGetResponse> getAllSchedule(Long showsId);

    // 회차 공연과 공연장 fetch join 선택 조회
    Schedule findScheduleWithShowsPlace(Long scheduleId, boolean fetchShows, boolean fetchPlace);
}
