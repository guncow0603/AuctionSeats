package me.kimgunwoo.auctionseats.domain.show.repository;

import me.kimgunwoo.auctionseats.domain.seat.dto.response.AuctionSeatInfoResponse;
import me.kimgunwoo.auctionseats.domain.seat.dto.response.SeatInfoResponse;
import org.springframework.stereotype.Repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static me.kimgunwoo.auctionseats.domain.auction.entity.QAuction.auction;
import static me.kimgunwoo.auctionseats.domain.grade.entity.QGrade.grade;
import static me.kimgunwoo.auctionseats.domain.grade.entity.QZoneGrade.zoneGrade;
import static me.kimgunwoo.auctionseats.domain.place.entity.QZone.zone;

@Repository
@RequiredArgsConstructor
public class ShowsRepositoryCustomImpl implements ShowsRepositoryCustom {

    private final JPAQueryFactory query;

    @Override
    public List<SeatInfoResponse> findShowsSeatInfo(Long showsId) {
        List<SeatInfoResponse> seatInfos = query
                .select(Projections.constructor(SeatInfoResponse.class,
                        zone.name,
                        grade.name,
                        grade.normalPrice,
                        zoneGrade.id
                ))
                .from(zoneGrade)
                .innerJoin(zoneGrade.grade, grade)
                .innerJoin(zoneGrade.zone, zone)
                .where(grade.shows.id.eq(showsId))
                .fetch();

        return seatInfos;
    }

    @Override
    public List<AuctionSeatInfoResponse> findShowsAuctionSeatInfo(Long scheduleId, Long showsId) {
        List<AuctionSeatInfoResponse> seatInfos = query
                .select(Projections.constructor(AuctionSeatInfoResponse.class,
                        zone.name,
                        grade.name,
                        grade.auctionPrice,
                        zoneGrade.id,
                        auction.id,
                        auction.seatNumber,
                        auction.endDateTime,
                        auction.isEnded
                ))
                .from(auction)
                .innerJoin(auction.zoneGrade, zoneGrade)
                .innerJoin(zoneGrade.grade, grade)
                .innerJoin(zoneGrade.zone, zone)
                .where(auction.schedule.id.eq(scheduleId))
                .where(grade.shows.id.eq(showsId))
                .fetch();
        return seatInfos;
    }
}
