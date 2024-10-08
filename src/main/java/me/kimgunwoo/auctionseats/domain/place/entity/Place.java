package me.kimgunwoo.auctionseats.domain.place.entity;

import jakarta.persistence.*;
import lombok.*;
import me.kimgunwoo.auctionseats.global.entity.BaseEntity;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Comment;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Table(name = "place")
public class Place extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Comment("공연장 이름")
    @Column(name = "name", length = 30, nullable = false)
    private String name;

    @Comment("공연장 주소")
    @Column(name = "address", length = 150, nullable = false)
    private String address;

    @Comment("총 좌석 개수")
    @Column(name = "count_seats", nullable = false)
    @ColumnDefault("0")
    private Integer countSeats = 0;

    @Comment("공연장 구역")
    @OneToMany(mappedBy = "place", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Zone> zones = new ArrayList<>();

    @Builder
    private Place(String name, String address, Integer countSeats) {
        this.name = name;
        this.address = address;
        this.countSeats = countSeats;
    }

    public void updateZone(List<Zone> zones) {
        for (Zone zone : zones) {
            zone.addPlace(this);
        }
        this.zones.addAll(zones);
    }
}
