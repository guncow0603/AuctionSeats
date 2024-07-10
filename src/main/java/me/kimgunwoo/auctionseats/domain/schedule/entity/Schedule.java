package me.kimgunwoo.auctionseats.domain.schedule.entity;

import jakarta.persistence.*;
import lombok.*;
import me.kimgunwoo.auctionseats.domain.show.entity.Shows;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Comment;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Comment("회차 수")
    @Column(name = "schedule", nullable = false)
    @ColumnDefault("0")
    private Integer schedule = 0;

    @Comment("공연 일시")
    @Column(name = "start_date_time", nullable = false)
    private LocalDateTime startDateTime;

    @Comment("공연 id")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "show_id")
    private Shows shows;


    @Builder
    private Schedule(int schedule, LocalDateTime startDateTime, Shows shows) {
        this.schedule = schedule;
        this.startDateTime = startDateTime;
        this.shows = shows;
    }
}
