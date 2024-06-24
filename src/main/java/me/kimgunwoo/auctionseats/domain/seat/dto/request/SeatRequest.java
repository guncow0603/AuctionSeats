package me.kimgunwoo.auctionseats.domain.seat.dto.request;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import me.kimgunwoo.auctionseats.domain.place.entity.Places;
import me.kimgunwoo.auctionseats.domain.seat.entity.Seat;

@Getter
@RequiredArgsConstructor
public class SeatRequest {
    @Size(min = 1, max = 10, message = "좌석 구역 입력은 필수입니다.")
    private final String zone;

    @NotNull(message = "구역당 좌석 개수는 필수입니다.")
    private final Integer zoneCountSeat;

    public Seat toEntity(Places places, Integer seatNumber) {
        return Seat
                .builder()
                .zone(this.zone)
                .seatNumber(seatNumber)
                .places(places)
                .build();
    }
}