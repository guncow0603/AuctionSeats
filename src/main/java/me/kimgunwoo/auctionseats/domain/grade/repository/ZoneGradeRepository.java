package me.kimgunwoo.auctionseats.domain.grade.repository;

import me.kimgunwoo.auctionseats.domain.grade.entity.ZoneGrade;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ZoneGradeRepository extends JpaRepository<ZoneGrade, Long> {
}