package me.kimgunwoo.auctionseats.domain.schedule.service;

import lombok.RequiredArgsConstructor;
import me.kimgunwoo.auctionseats.domain.schedule.entity.Schedule;
import me.kimgunwoo.auctionseats.domain.schedule.repository.ScheduleRepository;
import me.kimgunwoo.auctionseats.global.exception.ApiException;
import org.springframework.stereotype.Service;

import java.util.List;

import static me.kimgunwoo.auctionseats.global.exception.ErrorCode.NOT_FOUND_SEQUENCE;

@Service
@RequiredArgsConstructor
public class ScheduleServiceImpl implements ScheduleService {

    private final ScheduleRepository scheduleRepository;

    // 회차 리스트 저장
    @Override
    public void saveAllSchedule(List<Schedule> scheduleList) {
        scheduleRepository.saveAll(scheduleList);
    }

    // 회차 저장
    @Override
    public Schedule saveSchedule(Schedule schedule) {
        return scheduleRepository.save(schedule);
    }

    // 회차 탐색
    @Override
    public Schedule findSchedule(Long scheduleId) {
        return scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new ApiException(NOT_FOUND_SEQUENCE));
    }
}
