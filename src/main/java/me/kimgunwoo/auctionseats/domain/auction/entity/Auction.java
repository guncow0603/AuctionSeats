package me.kimgunwoo.auctionseats.domain.auction.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.kimgunwoo.auctionseats.domain.shows_schedule_seat.entity.ShowsScheduleSeat;
import me.kimgunwoo.auctionseats.global.entity.BaseEntity;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Comment;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "auction")
public class Auction extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Comment("시작가")
    @ColumnDefault("0")
    @Column(name = "start_price", nullable = false)
    private Long startPrice;

    @Comment("입찰가")
    @Column(name = "bid_price", nullable = false)
    private Long bidPrice;
    @Comment("시작일시")
    @Column(name = "start_date", nullable = false)
    private LocalDateTime startDateTime;

    @Comment("마감일시")
    @Column(name = "end_date", nullable = false)
    private LocalDateTime endDateTime;

    @Comment("종료여부 T - 종료 / F - 진행 중")
    @ColumnDefault("false")
    @Column(name = "is_ended")
    private Boolean isEnded = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seat_id")
    @JoinColumn(name = "schedule_id")
    private ShowsScheduleSeat scheduleSeat;

    @Builder
    private Auction(
            Long startPrice,
            LocalDateTime startDateTime,
            LocalDateTime endDateTime,
            ShowsScheduleSeat scheduleSeat
    ) {
        this.startPrice = startPrice;
        this.bidPrice = startPrice;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.scheduleSeat = scheduleSeat;
    }

    public void updateBidPrice(Long bidPrice) {
        this.bidPrice = bidPrice;
    }

    public void ended() {
        this.isEnded = true;
    }
}