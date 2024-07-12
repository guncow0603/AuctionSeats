package me.kimgunwoo.auctionseats.domain.schedule.service;

import lombok.RequiredArgsConstructor;
import me.kimgunwoo.auctionseats.domain.schedule.entity.Schedule;
import me.kimgunwoo.auctionseats.domain.schedule.repository.ScheduleRepository;
import me.kimgunwoo.auctionseats.domain.show.entity.Shows;
import me.kimgunwoo.auctionseats.global.exception.ApiException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import static me.kimgunwoo.auctionseats.global.exception.ErrorCode.NOT_FOUND_SCHEDULE;

@Service
@RequiredArgsConstructor
public class ScheduleServiceImpl implements ScheduleService {

    private final ScheduleRepository sequenceRepository;

    // 회차 조회
    @Override
    public Schedule findSchedule(Long scheduleId) {
        return sequenceRepository.findById(scheduleId)
                .orElseThrow(() -> new ApiException(NOT_FOUND_SCHEDULE));
    }

    // 회차 생성
    @Override
    public void createSchedule(Shows shows, LocalTime localTime) {

        List<Schedule> scheduleList = distributeSchedule(shows, localTime);
        sequenceRepository.saveAll(scheduleList);
    }

    //회차 요일 및 시작시간 부여
    @Override
    public List<Schedule> distributeSchedule(Shows shows, LocalTime startTime) {
        LocalDate startDate = shows.getStartDate();
        LocalDate endDate = shows.getEndDate();

        List<Schedule> distributeSequenceList = new ArrayList<>();
        long daysBetween = ChronoUnit.DAYS.between(startDate, endDate);

        for (int i = 1; i <= daysBetween; i++) {
            LocalDateTime dateTime = startDate.atTime(startTime);
            Schedule schedule =
                    Schedule
                            .builder()
                            .startDateTime(dateTime)
                            .shows(shows)
                            .schedule(i)
                            .build();
            distributeSequenceList.add(schedule);
            startDate = startDate.plusDays(1);
        }
        return distributeSequenceList;
    }

}
