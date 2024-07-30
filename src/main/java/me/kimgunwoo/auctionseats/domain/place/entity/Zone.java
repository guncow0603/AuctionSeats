package me.kimgunwoo.auctionseats.domain.place.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.kimgunwoo.auctionseats.global.entity.BaseEntity;
import org.hibernate.annotations.Comment;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "zone")
public class Zone extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Comment("구역명")
    @Column(name = "name", length = 10)
    private String name;

    @Comment("좌석수")
    @Column(name = "seat_number")
    private Integer seatNumber;

    @Comment("공연장")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "place_id")
    private Places places;

    @Builder
    private Zone(String name, Integer seatNumber) {
        this.name = name;
        this.seatNumber = seatNumber;
    }

    public void addPlace(Places places) {
        this.places = places;
    }

}


