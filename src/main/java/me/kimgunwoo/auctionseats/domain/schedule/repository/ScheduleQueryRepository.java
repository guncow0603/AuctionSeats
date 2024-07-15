package me.kimgunwoo.auctionseats.domain.schedule.repository;

import me.kimgunwoo.auctionseats.domain.schedule.entity.Schedule;

import java.util.Optional;

public interface ScheduleQueryRepository {

    Optional<Schedule> findByIdWithShowsInfo(Long id, boolean fetchShows, boolean fetchPlace);
}
