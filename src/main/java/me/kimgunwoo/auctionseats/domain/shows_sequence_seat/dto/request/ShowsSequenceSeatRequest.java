package me.kimgunwoo.auctionseats.domain.shows_sequence_seat.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import me.kimgunwoo.auctionseats.domain.place.dto.request.PlaceSeatAuctionInfo;

import java.util.List;

public record ShowsSequenceSeatRequest (
        @NotNull(message = "잘못된 경매 가격입니다")
        Long generalAuctionPrice,
        @NotNull(message = "잘못된 경매 가격입니다.")
        Long auctionPrice,
        @Valid
        @NotNull(message = "정확한 좌석 가격을 입력해 주세요.")
        List<PlaceSeatAuctionInfo> auctionSeats
        ){}
