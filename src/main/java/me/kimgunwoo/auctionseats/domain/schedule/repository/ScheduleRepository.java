package me.kimgunwoo.auctionseats.domain.schedule.repository;

import me.kimgunwoo.auctionseats.domain.schedule.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    @Query("select s from Schedule s "
            + "join fetch s.shows g "
            + "join fetch g.showsImage i "
            + "where s.id = :sequenceId and i.type = 'POSTER_IMG'")

    Optional<Schedule> findScheduleWithShowsById(Long sequenceId);
}