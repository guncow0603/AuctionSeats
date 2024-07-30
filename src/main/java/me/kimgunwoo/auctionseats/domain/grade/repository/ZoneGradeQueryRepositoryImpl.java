package me.kimgunwoo.auctionseats.domain.grade.repository;

import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import me.kimgunwoo.auctionseats.domain.grade.entity.ZoneGrade;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static me.kimgunwoo.auctionseats.domain.grade.entity.QGrade.grade;
import static me.kimgunwoo.auctionseats.domain.grade.entity.QZoneGrade.zoneGrade;
import static me.kimgunwoo.auctionseats.domain.place.entity.QZone.zone;

@Repository
@RequiredArgsConstructor
public class ZoneGradeQueryRepositoryImpl implements ZoneGradeQueryRepository {

    private final JPAQueryFactory query;

    @Override
    public Optional<ZoneGrade> findZoneGrade(Long id, boolean fetchZone, boolean fetchGrade) {
        JPAQuery<ZoneGrade> query = this.query.select(zoneGrade)
                .from(zoneGrade)
                .where(zoneGrade.id.eq(id));

        if (fetchGrade) {
            query.innerJoin(zoneGrade.grade, grade).fetchJoin();
        }
        if (fetchZone) {
            query.innerJoin(zoneGrade.zone, zone).fetchJoin();
        }

        return Optional.ofNullable(query.fetchOne());
    }
}
