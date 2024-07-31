package me.kimgunwoo.auctionseats.domain.show.entity;

import java.time.LocalDate;

import me.kimgunwoo.auctionseats.domain.place.entity.Place;
import me.kimgunwoo.auctionseats.global.entity.BaseEntity;
import org.hibernate.annotations.Comment;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "shows")
public class Shows extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Comment("공연 제목")
    @Column(name = "title", nullable = false)
    private String title;

    @Comment("공연 시작일")
    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Comment("공연 마감일")
    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;

    @Comment("공연")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shows_info")
    private ShowsInfo showsInfo;

    @Comment("공연장")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "place_id")
    private Place place;

    @Builder
    private Shows(String title, LocalDate startDate, LocalDate endDate, ShowsInfo showsInfo, Place place) {
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
        this.showsInfo = showsInfo;
        this.place = place;
    }

}