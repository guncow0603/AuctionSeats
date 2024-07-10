package me.kimgunwoo.auctionseats.domain.schedule.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import me.kimgunwoo.auctionseats.domain.schedule.entity.Schedule;
import me.kimgunwoo.auctionseats.domain.show.entity.Shows;

import java.time.LocalDateTime;

public record ScheduleRequest (
        @NotNull(message = "회차 정보는 필수입니다.")
        Integer schedule,
        @NotBlank(message = "공연일시 정보는 필수입니다.")
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
        LocalDateTime startDateTime){
    public Schedule toEntity(Shows shows) {
        return Schedule
                .builder()
                .schedule(this.schedule)
                .startDateTime(startDateTime)
                .shows(shows)
                .build();
    }
}
