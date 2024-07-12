package me.kimgunwoo.auctionseats.domain.reservation.dto.response;

import lombok.Builder;
import me.kimgunwoo.auctionseats.domain.reservation.dto.ReservationSeatInfo;

import java.time.LocalDateTime;
import java.util.List;

@Builder
public record ReservationDetailResponse(
        Long reservationId, // 예약 번호

        String username, // 예매자

        String title, // 제목

        LocalDateTime useDate, // 이용일

        String address, // 공연 장소

        List<ReservationSeatInfo> seats // 좌석 정보
){}
