package me.kimgunwoo.auctionseats.domain.seat.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.kimgunwoo.auctionseats.domain.place.entity.Places;
import me.kimgunwoo.auctionseats.domain.seat.dto.request.SeatRequest;
import me.kimgunwoo.auctionseats.global.entity.BaseEntity;
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
    private int seatNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "places_id")
    private Places places;

    public static Seat of(SeatRequest seatRequest, Places places) {
        return new Seat(seatRequest.getZone(), seatRequest.getSeatNumber(), places);
    }

    private Seat(String zone, int seatNumber, Places places) {
        this.zone = zone;
        this.seatNumber = seatNumber;
        this.places = places;
    }

}
