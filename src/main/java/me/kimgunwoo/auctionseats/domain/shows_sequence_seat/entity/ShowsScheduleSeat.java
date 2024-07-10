package me.kimgunwoo.auctionseats.domain.shows_sequence_seat.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.kimgunwoo.auctionseats.domain.schedule.entity.Schedule;
import me.kimgunwoo.auctionseats.global.entity.BaseEntity;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Comment;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "shows_sequence_seat")
public class ShowsScheduleSeat extends BaseEntity {

    @EmbeddedId
    private ShowsScheduleSeatID id;

    @Comment("가격")
    @Column(name = "price", nullable = false)
    @ColumnDefault("0")
    private Long price = 0L;

    @Comment("판매 타입 - NOMAL, AUCTION")
    @Column(name = "sell_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private SellType sellType;

    @Comment("판매 여부")
    @Column(name = "is_selled", nullable = false)
    @ColumnDefault("false")
    private Boolean isSelled = false;

    @Comment("좌석")
    @MapsId("seatId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seat_id")
    private Seat seat;

    @Comment("회차")
    @MapsId("sequenceId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sequence_id")
    private Schedule sequence;

    @Builder
    private ShowsScheduleSeat(Seat seat, Schedule sequence, Long price, SellType sellType, boolean isSelled) {
        this.seat = seat;
        this.sequence = sequence;
        this.price = price;
        this.sellType = sellType;
        this.isSelled = isSelled;
        this.id = new ShowsScheduleSeatID(seat.getId(), sequence.getId());
    }
    public void updateIsSelled() {
        this.isSelled = true;
    }
}