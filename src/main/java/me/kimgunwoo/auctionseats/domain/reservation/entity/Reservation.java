package me.kimgunwoo.auctionseats.domain.reservation.entity;

import lombok.Builder;
import me.kimgunwoo.auctionseats.domain.shows_sequence_seat.entity.ShowsSequenceSeat;
import me.kimgunwoo.auctionseats.domain.user.entity.User;
import me.kimgunwoo.auctionseats.global.entity.BaseEntity;
import org.hibernate.annotations.Comment;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinColumns;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "reservation")
public class Reservation extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Comment("공연 회차별 좌석")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({
            @JoinColumn(name = "seat_id", referencedColumnName = "seat_id"),
            @JoinColumn(name = "sequence_id", referencedColumnName = "sequence_id")
    })
    private ShowsSequenceSeat showsSequenceSeat;

    @Comment("유저")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
    @Comment("예매 가격")
    @Column(name = "price")
    private Long price;
    @Comment("예매 상태")
    @Column(name = "status", length = 10)
    @Enumerated(EnumType.STRING)
    private ReservationStatus status;

    @Builder
    private Reservation(User user, ShowsSequenceSeat showsSequenceSeat, Long price) {
        this.user = user;
        this.showsSequenceSeat = showsSequenceSeat;
        this.price = price;
        this.status = ReservationStatus.OK;
    }
}
