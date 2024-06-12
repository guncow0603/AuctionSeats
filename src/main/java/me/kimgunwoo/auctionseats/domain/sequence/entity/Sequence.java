package me.kimgunwoo.auctionseats.domain.sequence.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.kimgunwoo.auctionseats.domain.sequence.dto.request.SequenceRequest;
import me.kimgunwoo.auctionseats.domain.show.entity.Shows;
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
    @Column(name = "sequence")
    private int sequence;

    @Comment("공연 일시")
    @Column(name = "start_date_time")
    private LocalDateTime startDateTime;

    @Comment("공연 id")
    @ManyToOne
    @JoinColumn(name = "show_id")
    private Shows shows;

    public static Sequence of(SequenceRequest sequenceRequest, Shows shows) {
        return new Sequence(sequenceRequest.sequence(), sequenceRequest.startDateTime(), shows);
    }

    private Sequence(int sequence, LocalDateTime startDateTime, Shows shows) {
        this.sequence = sequence;
        this.startDateTime = startDateTime;
        this.shows = shows;
    }
}
