package me.kimgunwoo.auctionseats.domain.auction.dto.request;


import jakarta.validation.constraints.NotNull;
import me.kimgunwoo.auctionseats.domain.auction.entity.Auction;
import me.kimgunwoo.auctionseats.domain.grade.entity.ZoneGrade;
import me.kimgunwoo.auctionseats.domain.schedule.entity.Schedule;

import java.time.LocalDateTime;

public record AuctionCreateRequest(
        @NotNull(message = "경매 좌석 번호를 입력해주세요.") Integer seatNumber) {

    public Auction toEntity(Schedule schedule, ZoneGrade zoneGrade) {
        LocalDateTime now = LocalDateTime.now();

        return Auction.builder()
                .schedule(schedule)
                .zoneGrade(zoneGrade)
                .seatNumber(seatNumber)
                .startPrice(zoneGrade.getGrade().getAuctionPrice())
                .startDateTime(now)
                .endDateTime(schedule.getStartDateTime().minusDays(1)) // 경매 종료 시간을 시작 시간보다 1일 전으로 설정
                .build();
    }
}
