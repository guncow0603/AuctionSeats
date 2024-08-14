package me.kimgunwoo.auctionseats.domain.schedule.dto.response;

import me.kimgunwoo.auctionseats.domain.schedule.entity.Schedule;

import java.time.LocalDateTime;

public record ScheduleGetResponse(
        Long scheduleId,
        Integer sequence,
        LocalDateTime startDateTime
) {
    public ScheduleGetResponse(Schedule schedule) {
        this(schedule.getId(), schedule.getSequence(), schedule.getStartDateTime());
    }
}

