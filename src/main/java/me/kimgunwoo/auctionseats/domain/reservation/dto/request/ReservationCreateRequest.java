package me.kimgunwoo.auctionseats.domain.reservation.dto.request;

import me.kimgunwoo.auctionseats.domain.reservation.entity.Reservation;
import me.kimgunwoo.auctionseats.domain.shows_sequence_seat.entity.ShowsSequenceSeat;
import me.kimgunwoo.auctionseats.domain.user.entity.User;

public record ReservationCreateRequest (
        String zone,
        Long price){
    public Reservation toEntity(User user, ShowsSequenceSeat showsSequenceSeat, Long price) {
        return Reservation.builder()
                .user(user)
                .showsSequenceSeat(showsSequenceSeat)
                .price(price)
                .build();
    }
}
