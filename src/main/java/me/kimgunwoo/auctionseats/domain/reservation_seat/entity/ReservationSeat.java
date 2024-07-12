package me.kimgunwoo.auctionseats.domain.reservation_seat.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import me.kimgunwoo.auctionseats.domain.grade.entity.ZoneGrade;
import me.kimgunwoo.auctionseats.domain.reservation.entity.Reservation;
import me.kimgunwoo.auctionseats.domain.schedule.entity.Schedule;
import me.kimgunwoo.auctionseats.global.entity.BaseEntity;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "reservation_seat")
public class ReservationSeat extends BaseEntity {

    @EmbeddedId
    private ReservationSeatId id;

    @MapsId("zoneGradeId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "zone_grade_id")
    private ZoneGrade zoneGrade;

    @MapsId("scheduleId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "schedule_id")
    private Schedule schedule;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reservation_id")
    private Reservation reservation;

    @Builder
    public ReservationSeat(ReservationSeatId id, ZoneGrade zoneGrade, Schedule schedule) {
        this.id = id;
        this.zoneGrade = zoneGrade;
        this.schedule = schedule;
    }
}