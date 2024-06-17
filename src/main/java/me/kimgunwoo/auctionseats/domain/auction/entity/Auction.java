package me.kimgunwoo.auctionseats.domain.auction.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.kimgunwoo.auctionseats.domain.auction.dto.request.AuctionCreateRequest;
import me.kimgunwoo.auctionseats.global.entity.BaseEntity;
import org.hibernate.annotations.Comment;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "auction")
@Entity
public class Auction extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Comment("시작가")
    @Column(name = "start_price", nullable = false)
    private long startPrice;

    @Comment("입찰가")
    @Column(name = "start_price", nullable = false)
    private long bidPrice;
    @Comment("시작일시")
    @Column(name = "start_date", nullable = false)
    private LocalDateTime startDateTime;

    @Comment("마감일시")
    @Column(name = "end_date", nullable = false)
    private LocalDateTime endDateTime;

    @Comment("종료여부 T - 종료 / F - 진행 중")
    @Column(name = "is_ended")
    private boolean isEnded;

    private Auction(long startPrice, LocalDateTime startDateTime, LocalDateTime endDateTime) {
        this.startPrice = startPrice;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
    }

    public static Auction of(AuctionCreateRequest request) {
        return new Auction(
                request.startPrice(),
                request.startDateTime(),
                request.endDateTime()
        );
    }
}
