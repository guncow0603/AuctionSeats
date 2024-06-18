package me.kimgunwoo.auctionseats.domain.shows_sequence_seat.entity;

import me.kimgunwoo.auctionseats.domain.seat.entity.Seat;
import me.kimgunwoo.auctionseats.domain.sequence.entity.Sequence;
import me.kimgunwoo.auctionseats.domain.shows_sequence_seat.dto.request.ShowsSequenceSeatRequest;
import me.kimgunwoo.auctionseats.global.entity.BaseEntity;
import org.hibernate.annotations.Comment;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "shows_sequence_seat")
public class ShowsSequenceSeat extends BaseEntity {

    @EmbeddedId
    private ShowsSequenceSeatID id;

    @Comment("가격")
    @Column(name = "price")
    private Long price;

    @Comment("판매 타입 - 경매, 일반")
    @Column(name = "sell_type")
    private String sellType;

    @Comment("판매 여부")
    @Column(name = "is_selled")
    private boolean isSelled = false;

    @Comment("좌석")
    @MapsId("seatId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seat_id")
    private Seat seat;

    @Comment("회차")
    @MapsId("sequenceId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sequence_id")
    private Sequence sequence;

    public static ShowsSequenceSeat generalOf(
            Seat seat,
            Sequence sequence,
            ShowsSequenceSeatRequest showsSequenceSeatRequest
    ) {
        return new ShowsSequenceSeat(
                seat,
                sequence,
                showsSequenceSeatRequest.generalAuctionPrice(),
                "일반",
                false
        );
    }

    public static ShowsSequenceSeat auctionOf(
            Seat seat,
            Sequence sequence,
            ShowsSequenceSeatRequest showsSequenceSeatRequest
    ) {
        return new ShowsSequenceSeat(
                seat,
                sequence,
                showsSequenceSeatRequest.generalAuctionPrice(),
                "경매",
                false
        );
    }

    private ShowsSequenceSeat(Seat seat, Sequence sequence, Long price, String sellType, boolean isSelled) {
        this.seat = seat;
        this.sequence = sequence;
        this.price = price;
        this.sellType = sellType;
        this.isSelled = isSelled;
    }

}