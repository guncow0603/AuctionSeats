package me.kimgunwoo.auctionseats.domain.reservation.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;
import me.kimgunwoo.auctionseats.domain.reservation.entity.ReservationStatus;

import java.time.LocalDateTime;

@Getter
@Builder
public record ReservationResponse(
        Long reservationId,
        LocalDateTime reservationDate,
        String title,

        @JsonFormat(pattern = "yyyy.MM.dd HH:mm")
        LocalDateTime useDate,

        Integer numberOfTicket,
        Long price,

        @JsonFormat(pattern = "yyyy.MM.dd HH:mm")
        LocalDateTime cancelDeadline,
        ReservationStatus status
) {

    public ReservationResponse() {
        this(null, null, null, null, null, null, null, null);
    }

    public ReservationResponse(Long reservationId, LocalDateTime reservationDate, String title, LocalDateTime useDate,
                               Integer numberOfTicket, Long price, LocalDateTime cancelDeadline, ReservationStatus status) {
        this.reservationId = reservationId;
        this.reservationDate = reservationDate;
        this.title = title;
        this.useDate = useDate;
        this.numberOfTicket = numberOfTicket;
        this.price = price;
        this.cancelDeadline = cancelDeadline;
        this.status = status;
    }
}
