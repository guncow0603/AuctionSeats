package me.kimgunwoo.auctionseats.domain.admin.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import me.kimgunwoo.auctionseats.domain.place.entity.Places;
import me.kimgunwoo.auctionseats.domain.show.entity.Shows;
import me.kimgunwoo.auctionseats.domain.show.entity.ShowsInfo;

import java.time.LocalDate;
import java.time.LocalTime;


public record ShowsCreateRequest(
        @NotBlank(message = "공연제목 기입은 필수 입니다.")
        @Pattern(regexp = "(\\S+)-(\\S+)", message = "입력 양식: (공연제목)-(지역 및 목표) 입니다.")
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
        public Shows toEntity(Places places, ShowsInfo showsInfo) {
                return Shows
                        .builder()
                        .title(title)
                        .places(places)
                        .showsInfo(showsInfo)
                        .startDate(this.startDate)
                        .endDate(this.endDate)
                        .build();
        }
}
