package me.kimgunwoo.auctionseats.domain.reservation.dto.response;

import lombok.Builder;

import java.time.LocalDateTime;
@Builder
public record ReservationDetailResponse(
         Long reservationId,// 예약 번호

         String username, // 유저 이름

         LocalDateTime reservationDate, // 예약일자

         String goodsTitle,// 공연 제목

         int sequence, // 회차

         String zone, // 좌석 구역

         Integer seatNumber, // 좌석 번호

         String address, // 예약 장소

         LocalDateTime goodsStartDateTime, // 이용일자

         String thumbnailUrl // 썸네일 이미지
) {
    public static ReservationDetailResponse from(
            Long reservationId,
            String username,
            LocalDateTime reservationDate,
            String goodsTitle,
            int sequence,
            String zone,
            Integer seatNumber,
            String address,
            LocalDateTime goodsStartDateTime,
            String thumbnailUrl
    ) {
        return ReservationDetailResponse.builder()
                .reservationId(reservationId)
                .username(username)
                .reservationDate(reservationDate)
                .goodsTitle(goodsTitle)
                .sequence(sequence)
                .zone(zone)
                .seatNumber(seatNumber)
                .address(address)
                .goodsStartDateTime(goodsStartDateTime)
                .thumbnailUrl(thumbnailUrl)
                .build();
    }
}
