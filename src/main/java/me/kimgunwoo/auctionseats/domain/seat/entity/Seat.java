package me.kimgunwoo.auctionseats.domain.seat.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.kimgunwoo.auctionseats.domain.place.entity.Places;
import me.kimgunwoo.auctionseats.global.entity.BaseEntity;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Comment;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "seat")
public class Seat extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Comment("구역명")
    @Column(name = "zone")
    private String zone;

    @Comment("좌석번호")
    @Column(name = "seat_number")
    @ColumnDefault("0")
    private Integer seatNumber = 0;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "places_id")
    private Places places;

    @Builder
    private Seat(String zone, int seatNumber, Places places) {
        this.zone = zone;
        this.seatNumber = seatNumber;
        this.places = places;
    }

}
