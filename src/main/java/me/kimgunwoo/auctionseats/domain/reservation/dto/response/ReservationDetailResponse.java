package me.kimgunwoo.auctionseats.domain.reservation.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;
import me.kimgunwoo.auctionseats.domain.reservation.dto.ReservationSeatInfo;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public record ReservationDetailResponse(
        Long reservationId,
        String username,
        String title,
        Long price,

        @JsonFormat(pattern = "yyyy.MM.dd HH:mm")
        LocalDateTime useDate,

        String address,
        List<ReservationSeatInfo> seats
) {

    public ReservationDetailResponse() {
        this(null, null, null, null, null, null, null);
    }

    public ReservationDetailResponse(Long reservationId, String username, String title, Long price,
                                     LocalDateTime useDate, String address, List<ReservationSeatInfo> seats) {
        this.reservationId = reservationId;
        this.username = username;
        this.title = title;
        this.price = price;
        this.useDate = useDate;
        this.address = address;
        this.seats = seats;
    }
}

