package me.kimgunwoo.auctionseats.domain.schedule.dto.response;

import lombok.Getter;
import me.kimgunwoo.auctionseats.domain.schedule.entity.Schedule;

import java.time.LocalDateTime;

@Getter
public class ScheduleGetResponse {
    private final Long scheduleId;

    private final Integer sequence;

    private final LocalDateTime startDateTime;

    public ScheduleGetResponse(Schedule schedule) {
        this.scheduleId = schedule.getId();
        this.sequence = schedule.getSequence();
        this.startDateTime = schedule.getStartDateTime();
    }
}
