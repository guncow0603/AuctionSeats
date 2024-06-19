package me.kimgunwoo.auctionseats.domain.shows_sequence_seat.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.kimgunwoo.auctionseats.domain.seat.entity.Seat;
import me.kimgunwoo.auctionseats.domain.sequence.entity.Sequence;
import me.kimgunwoo.auctionseats.domain.shows_sequence_seat.dto.request.ShowsSequenceSeatRequest;
import me.kimgunwoo.auctionseats.global.entity.BaseEntity;
import org.hibernate.annotations.Comment;

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

    @Comment("판매 타입 - NOMAL, ACTION")
    @Column(name = "sell_type")
    @Enumerated(EnumType.STRING)
    private SellType sellType;

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
                SellType.NORMAL,
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
                SellType.AUCTION,
                false
        );
    }

    private ShowsSequenceSeat(Seat seat, Sequence sequence, Long price, SellType sellType, boolean isSelled) {
        this.seat = seat;
        this.sequence = sequence;
        this.price = price;
        this.sellType = sellType;
        this.isSelled = isSelled;
    }

}