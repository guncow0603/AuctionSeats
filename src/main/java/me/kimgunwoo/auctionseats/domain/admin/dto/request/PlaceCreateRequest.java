package me.kimgunwoo.auctionseats.domain.admin.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import me.kimgunwoo.auctionseats.domain.place.dto.ZoneInfo;
import me.kimgunwoo.auctionseats.domain.place.entity.Place;

import java.util.List;


public record PlaceCreateRequest(
        @Size(min = 1, max = 30, message = "공연장 이름은 필수입니다.")
        String name,

        @Size(min = 1, max = 150, message = "주소 입력은 필수입니다.")
        String address,

        @NotNull(message = "구역 정보 입력은 필수입니다.")
        List<ZoneInfo> zoneInfos
) {
        public Place toEntity(Integer countSeats) {
                return Place
                        .builder()
                        .name(this.name)
                        .address(this.address)
                        .countSeats(countSeats)
                        .build();

        }
}
