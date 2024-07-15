package me.kimgunwoo.auctionseats.domain.schedule.repository;

import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import me.kimgunwoo.auctionseats.domain.schedule.entity.Schedule;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static me.kimgunwoo.auctionseats.domain.schedule.entity.QSchedule.schedule;
import static me.kimgunwoo.auctionseats.domain.show.entity.QShows.shows;

@Repository
@RequiredArgsConstructor
public class ScheduleQueryRepositoryImpl implements ScheduleQueryRepository {

    private final JPAQueryFactory query;

    @Override
    public Optional<Schedule> findByIdWithShowsInfo(Long id, boolean fetchShows, boolean fetchPlace) {
        JPAQuery<Schedule> jpaQuery = query.select(schedule)
                .from(schedule)
                .where(schedule.id.eq(id));

        if (fetchShows) {
            jpaQuery.innerJoin(schedule.shows, shows).fetchJoin();
        }

        if (fetchPlace) {
            jpaQuery.innerJoin(shows.places).fetchJoin();
        }

        return Optional.ofNullable(jpaQuery.fetchOne());
    }
}