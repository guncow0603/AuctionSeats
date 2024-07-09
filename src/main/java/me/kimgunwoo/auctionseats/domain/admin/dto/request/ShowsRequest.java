package me.kimgunwoo.auctionseats.domain.admin.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import me.kimgunwoo.auctionseats.domain.show.entity.ShowsInfo;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;


public record ShowsRequest (
        @Size(min = 1, max = 30, message = "1~30자 사이로 입력해주세요")
        String name,
        @Size(min = 1, max = 150, message = "1~150자 사이로 입력해주세요")
        String description,

        @NotNull(message = "공연 시작일 기입은 필수입니다.")
        @JsonFormat(pattern = "yyyy-MM-dd")

        LocalDate startDate,
        @NotNull(message = "공연 종료일은 필수입니다.")
        @JsonFormat(pattern = "yyyy-MM-dd")
        LocalDate endDate,

        @NotNull(message = "연령 입력은 필수입니다.")
        Integer ageGrade,
        @NotNull(message = "상영 시간은 필수입니다")
        @JsonFormat(pattern = "HH:mm")
        LocalTime startTime,

        @NotNull(message = "공연 시간은 필수입니다")
        Integer runningTime,
        @Size(min = 1, max = 30, message = "카테고리 입력은 필수입니다.")
        String categoryName){
        public ShowsInfo toEntity() {
                return ShowsInfo.builder().name(this.name)
                        .description(this.description)
                        .ageGrade(this.ageGrade)
                        .runningTime(this.runningTime)
                        .showsImage(new ArrayList<>())
                        .build();}
}
