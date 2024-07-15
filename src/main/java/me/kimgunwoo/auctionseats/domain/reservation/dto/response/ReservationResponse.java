package me.kimgunwoo.auctionseats.domain.reservation.dto.response;

import lombok.Builder;
import me.kimgunwoo.auctionseats.domain.reservation.entity.ReservationStatus;

import java.time.LocalDateTime;
@Builder
public record ReservationResponse (
        Long reservationId, // 예약번호

        LocalDateTime reservationDate, // 예매일

        String title, // 상품명

        LocalDateTime useDate, // 이용일

        Integer numberOfTicket, // 매수

        LocalDateTime cancelDeadline, // 취소가능일

        ReservationStatus status // 상태
){
}
