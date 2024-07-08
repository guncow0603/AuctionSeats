package me.kimgunwoo.auctionseats.domain.admin.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import me.kimgunwoo.auctionseats.domain.seat.dto.request.SeatRequest;
import me.kimgunwoo.auctionseats.domain.seat.entity.Seat;
import me.kimgunwoo.auctionseats.domain.sequence.entity.Sequence;
import me.kimgunwoo.auctionseats.domain.shows_sequence_seat.entity.SellType;
import me.kimgunwoo.auctionseats.domain.shows_sequence_seat.entity.ShowsSequenceSeat;

import java.util.List;

public record ShowsSequenceSeatRequest (
        @NotNull(message = "잘못된 경매 가격입니다")
        Long generalAuctionPrice,
        @NotNull(message = "잘못된 경매 가격입니다.")
        Long auctionPrice,

        @NotBlank(message = "구역 입력은 필수입니다.")
        String zone,
        List<Integer> auctionSeats

        ){
        public ShowsSequenceSeat generalToEntity(Seat seat, Sequence sequence) {
                return ShowsSequenceSeat
                        .builder()
                        .price(this.generalAuctionPrice)
                        .seat(seat)
                        .sequence(sequence)
                        .sellType(SellType.NORMAL)
                        .isSelled(false)
                        .build();
        }

        public ShowsSequenceSeat auctionToEntity(Seat seat, Sequence sequence) {
                return ShowsSequenceSeat
                        .builder()
                        .price(this.auctionPrice)
                        .seat(seat)
                        .sequence(sequence)
                        .sellType(SellType.AUCTION)
                        .isSelled(false)
                        .build();
        }
}
