package me.kimgunwoo.auctionseats.domain.show.dto.request;

import java.time.LocalDateTime;

public record ShowsRequest (
        String name,
        String description,
        LocalDateTime startDate,
        LocalDateTime endDate,
        int ageGrade,
        String runningTime,
        String categoryName){
}
