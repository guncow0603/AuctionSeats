package me.kimgunwoo.auctionseats.domain.schedule.repository;

import me.kimgunwoo.auctionseats.domain.schedule.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
}