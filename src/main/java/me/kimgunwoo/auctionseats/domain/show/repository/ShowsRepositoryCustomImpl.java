package me.kimgunwoo.auctionseats.domain.show.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import me.kimgunwoo.auctionseats.domain.seat.dto.response.AuctionSeatInfoResponse;
import me.kimgunwoo.auctionseats.domain.seat.dto.response.SeatInfoResponse;
import me.kimgunwoo.auctionseats.domain.show.dto.response.ShowsGetQueryResponse;
import me.kimgunwoo.auctionseats.domain.show.entity.ImageType;
import me.kimgunwoo.auctionseats.domain.show.entity.QShowsImage;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

import static me.kimgunwoo.auctionseats.domain.auction.entity.QAuction.auction;
import static me.kimgunwoo.auctionseats.domain.grade.entity.QGrade.grade;
import static me.kimgunwoo.auctionseats.domain.grade.entity.QZoneGrade.zoneGrade;
import static me.kimgunwoo.auctionseats.domain.place.entity.QZone.zone;
import static me.kimgunwoo.auctionseats.domain.show.entity.QShows.shows;
import static me.kimgunwoo.auctionseats.domain.show.entity.QShowsCategory.showsCategory;
import static me.kimgunwoo.auctionseats.domain.show.entity.QShowsInfo.showsInfo;

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

    @Override
    public List<ShowsGetQueryResponse> findAllByShowsAndCategoryName(
            Long cursorId, int size, String categoryName) {

        QShowsImage showsImageSubQuery = new QShowsImage("showsImageSub");
        JPAQuery<ShowsGetQueryResponse> query = this.query
                .select(
                        Projections.constructor(
                                ShowsGetQueryResponse.class,
                                shows.id,
                                shows.title,
                                JPAExpressions
                                        .select(showsImageSubQuery.s3Key)
                                        .from(showsImageSubQuery)
                                        .where(showsImageSubQuery.showsInfo.id.eq(shows.showsInfo.id)
                                                .and(showsImageSubQuery.type.eq(ImageType.POSTER_IMG)))
                                        .limit(1)
                        ))
                .from(shows)
                .leftJoin(shows.showsInfo, showsInfo)
                .leftJoin(showsInfo.showsCategory, showsCategory)
                .where(shows.endDate.after(LocalDate.now()));

        if (categoryName != null) {
            query.where(showsCategory.name.eq(categoryName));
        }

        if (cursorId != null) {
            query.where(shows.id.lt(cursorId));
        }

        return query
                .limit(size)
                .orderBy(shows.id.desc())
                .fetch();
    }
}
