package me.kimgunwoo.auctionseats.domain.user.repository;

import me.kimgunwoo.auctionseats.domain.user.entity.Point;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PointRepository extends JpaRepository<Point, Long> ,PointRepositoryCustom{
}
