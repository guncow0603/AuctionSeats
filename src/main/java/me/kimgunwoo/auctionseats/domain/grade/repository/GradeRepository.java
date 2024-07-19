package me.kimgunwoo.auctionseats.domain.grade.repository;

import me.kimgunwoo.auctionseats.domain.grade.entity.Grade;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GradeRepository extends JpaRepository<Grade, Long> {
    // 해당 공현의 등급 전체 조회
    List<Grade> findAllByShowsId(Long showsId);
}
