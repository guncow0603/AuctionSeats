package me.kimgunwoo.auctionseats.domain.sequence.entity;

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
public class Sequence {
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
    @JoinColumn(name = "show_id")
    private Shows shows;


    @Builder
    private Sequence(int sequence, LocalDateTime startDateTime, Shows shows) {
        this.sequence = sequence;
        this.startDateTime = startDateTime;
        this.shows = shows;
    }
}
