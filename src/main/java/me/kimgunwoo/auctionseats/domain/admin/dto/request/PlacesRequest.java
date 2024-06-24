package me.kimgunwoo.auctionseats.domain.admin.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import me.kimgunwoo.auctionseats.domain.place.entity.Places;
import me.kimgunwoo.auctionseats.domain.seat.dto.request.SeatRequest;

import java.util.List;


public record PlacesRequest(
        @Size(min = 1, max = 30, message = "공연장 이름은 필수입니다.")
        String name,

        @Size(min = 1, max = 150, message = "주소 입력은 필수입니다.")
        String address,

        @Valid
        @NotEmpty(message = "좌석 정보는 필수입니다.")
        List<SeatRequest> seats
) {
        public Places toEntity(Integer countSeats) {
                return Places
                        .builder()
                        .name(this.name)
                        .address(this.address)
                        .countSeats(countSeats)
                        .build();

        }
}
