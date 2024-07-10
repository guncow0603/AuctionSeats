package me.kimgunwoo.auctionseats.domain.schedule.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.kimgunwoo.auctionseats.domain.show.entity.Shows;
import me.kimgunwoo.auctionseats.global.entity.BaseEntity;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Comment;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "schedule")
public class Schedule extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Comment("회차 수")
    @Column(name = "sequence", nullable = false)
    @ColumnDefault("0")
    private Integer sequence = 0;

    @Comment("공연 일시")
    @Column(name = "start_date_time", nullable = false)
    private LocalDateTime startDateTime;

    @Comment("공연 id")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shows_id")
    private Shows shows;

    @Builder
    private Schedule(int sequence, LocalDateTime startDateTime, Shows shows) {
        this.sequence = sequence;
        this.startDateTime = startDateTime;
        this.shows = shows;
    }
}
