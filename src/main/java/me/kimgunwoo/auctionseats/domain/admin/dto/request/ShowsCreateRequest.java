package me.kimgunwoo.auctionseats.domain.admin.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import me.kimgunwoo.auctionseats.domain.place.entity.Place;
import me.kimgunwoo.auctionseats.domain.show.entity.Shows;
import me.kimgunwoo.auctionseats.domain.show.entity.ShowsInfo;

import java.time.LocalDate;
import java.time.LocalTime;


public record ShowsCreateRequest(
        @NotNull(message = "공연제목 기입은 필수 입니다.")
        @Pattern(
                regexp = "([A-Za-z0-9\\s(){}<>가-힣]+) - ([A-Za-z0-9\\s(){}<>가-힣]+)",
                message = "입력 양식: (공연제목) - (지역 및 목표) 입니다.")
        String title,

        @NotNull(message = "공연 시작일 기입은 필수입니다.")
        @JsonFormat(pattern = "yyyy-MM-dd")
        LocalDate startDate,
        @NotNull(message = "공연 종료일은 필수입니다.")
        @JsonFormat(pattern = "yyyy-MM-dd")
        LocalDate endDate,
        @NotNull(message = "상영 시간은 필수입니다")
        @JsonFormat(pattern = "HH:mm")
        LocalTime startTime){
        public Shows toEntity(Place place, ShowsInfo showsInfo) {
                return Shows
                        .builder()
                        .title(title)
                        .place(place)
                        .showsInfo(showsInfo)
                        .startDate(this.startDate)
                        .endDate(this.endDate)
                        .build();
        }
}
