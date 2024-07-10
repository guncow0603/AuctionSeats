package me.kimgunwoo.auctionseats.domain.auction.dto.request;


import jakarta.validation.constraints.NotNull;
import me.kimgunwoo.auctionseats.domain.auction.entity.Auction;
import me.kimgunwoo.auctionseats.domain.grade.entity.ZoneGrade;
import me.kimgunwoo.auctionseats.domain.schedule.entity.Schedule;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public record AuctionCreateRequest (

    @NotNull(message = "경매 좌석 번호를 입력해주세요.")
    Integer seatNumber
    ){
    public Auction toEntity(Schedule schedule, ZoneGrade zoneGrade) {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH");
        String now = LocalDateTime.now().format(format);

        return Auction.builder()
                .schedule(schedule)
                .zoneGrade(zoneGrade)
                .seatNumber(seatNumber)
                .startPrice(zoneGrade.getGrade().getAuctionPrice())
                .bidPrice(zoneGrade.getGrade().getAuctionPrice())
                .startDateTime(LocalDateTime.parse(now, format))
                .endDateTime(schedule.getStartDateTime().minusDays(3))
                .build();
    }
}

