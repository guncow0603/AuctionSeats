package me.kimgunwoo.auctionseats.domain.show.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;


import java.time.LocalDate;


public record ShowsRequest (
        @Size(max = 30)
        @NotBlank
        String name,
        @Size(max = 150)
        @NotBlank
        String description,

        @NotBlank
        @JsonFormat(pattern = "yyyy-MM-dd")

        LocalDate startDate,
        @NotBlank
        @JsonFormat(pattern = "yyyy-MM-dd")
        LocalDate endDate,

        @NotBlank
        Integer ageGrade,
        @NotBlank
        Integer runningTime,
        @Size(max = 30)
        @NotBlank
        String categoryName){
}
