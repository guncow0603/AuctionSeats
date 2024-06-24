package me.kimgunwoo.auctionseats.domain.place.entity;

import jakarta.persistence.*;
import lombok.*;
import me.kimgunwoo.auctionseats.global.entity.BaseEntity;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Comment;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Table(name = "places")
public class Places extends BaseEntity {
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
    @Builder
    private Places(String name, String address, Integer countSeats) {
        this.name = name;
        this.address = address;
        this.countSeats = countSeats;
    }
}
