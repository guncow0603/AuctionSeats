package me.kimgunwoo.auctionseats.domain.show.repository;

import me.kimgunwoo.auctionseats.domain.show.entity.ShowsImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ShowsImageRepository extends JpaRepository<ShowsImage, Long> {
    // 대표 이미지 조회
    Optional<ShowsImage> findThumbnailByShowsId(Long showsId);
}
