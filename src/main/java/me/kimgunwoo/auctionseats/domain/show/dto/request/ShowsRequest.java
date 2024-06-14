package me.kimgunwoo.auctionseats.domain.show.dto.request;

import java.time.LocalDate;

public record ShowsRequest (
        String name,
        String description,
        LocalDate startDate,
        LocalDate endDate,
        int ageGrade,
        String runningTime,
        String categoryName){
}
