package me.kimgunwoo.auctionseats.domain.grade.repository;

import me.kimgunwoo.auctionseats.domain.grade.entity.Grade;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GradeRepository extends JpaRepository<Grade, Long> {
}
