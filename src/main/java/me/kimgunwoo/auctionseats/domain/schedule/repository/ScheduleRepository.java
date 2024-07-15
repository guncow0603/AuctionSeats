package me.kimgunwoo.auctionseats.domain.schedule.repository;

import me.kimgunwoo.auctionseats.domain.schedule.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule, Long>, ScheduleQueryRepository {
    
    // 공연Id에 해당하는 모든 회차 조회
    List<Schedule> findAllByShowsId(Long showsId);
}