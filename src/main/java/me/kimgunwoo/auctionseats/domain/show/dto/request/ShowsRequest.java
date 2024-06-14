package me.kimgunwoo.auctionseats.domain.show.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

public record ShowsRequest (
        @Size(max = 30)
        @NotBlank
        String name,
        @Size(max = 150)
        @NotBlank
        String description,

        @NotBlank
        @JsonFormat(pattern = "yyyy-MM-dd")
        LocalDateTime startDate,
        @NotBlank
        @JsonFormat(pattern = "yyyy-MM-dd")
        LocalDateTime endDate,
        @NotBlank
        Integer ageGrade,
        @NotBlank
        Integer runningTime,
        @Size(max = 30)
        @NotBlank
        String categoryName){
}
