package me.kimgunwoo.auctionseats.domain.grade.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.kimgunwoo.auctionseats.domain.show.entity.Shows;
import me.kimgunwoo.auctionseats.global.entity.BaseEntity;
import org.hibernate.annotations.Comment;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "grade")
public class Grade extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Comment("좌석등급명")
    @Column(name = "name", length = 30, nullable = false)
    private String name;

    @Comment("일반가격")
    @Column(name = "normal_price", nullable = false)
    private Long normalPrice;

    @Comment("경매가격")
    @Column(name = "auctionPrice", nullable = false)
    private Long auctionPrice;

    @Comment("공연")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shows_id")
    private Shows shows;

    @Builder
    private Grade(String name, Long normalPrice, Long auctionPrice, Shows shows) {
        this.name = name;
        this.normalPrice = normalPrice;
        this.auctionPrice = auctionPrice;
        this.shows = shows;
    }
}