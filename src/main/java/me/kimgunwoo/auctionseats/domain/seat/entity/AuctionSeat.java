package me.kimgunwoo.auctionseats.domain.seat.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.kimgunwoo.auctionseats.domain.sequence.entity.Sequence;
import me.kimgunwoo.auctionseats.global.entity.BaseEntity;
import org.hibernate.annotations.Comment;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "auction_seat")
public class AuctionSeat extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Comment("좌석번호")
    @Column(name = "seat_number", nullable = false)
    private Integer seatNumber;

    @Comment("회차")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sequence_id", nullable = false)
    private Sequence sequence;

    @Comment("구역등급")
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "zone_grade_id", nullable = false)
    private ZoneGrade zoneGrade;

    @Builder
    public AuctionSeat(Integer seatNumber, Sequence sequence, ZoneGrade zoneGrade) {
        this.seatNumber = seatNumber;
        this.sequence = sequence;
        this.zoneGrade = zoneGrade;
    }
}
