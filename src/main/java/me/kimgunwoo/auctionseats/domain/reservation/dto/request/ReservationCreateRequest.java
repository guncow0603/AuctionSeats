package me.kimgunwoo.auctionseats.domain.reservation.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import me.kimgunwoo.auctionseats.domain.reservation.entity.Reservation;
import me.kimgunwoo.auctionseats.domain.shows_sequence_seat.entity.ShowsScheduleSeat;
import me.kimgunwoo.auctionseats.domain.user.entity.User;

public record ReservationCreateRequest (
        @NotBlank(message = "구역을 입력하세요.")
        String zone,
        @NotNull(message = "가격을 입력하세요.")
        Long price){
    public Reservation toEntity(User user, ShowsScheduleSeat showsScheduleSeat, Long price) {
        return Reservation.builder()
                .user(user)
                .showsScheduleSeat(showsScheduleSeat)
                .price(price)
                .build();
    }
}
