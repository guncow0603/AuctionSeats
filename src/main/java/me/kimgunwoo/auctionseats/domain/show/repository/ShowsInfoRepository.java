package me.kimgunwoo.auctionseats.domain.show.repository;

import io.lettuce.core.dynamic.annotation.Param;
import me.kimgunwoo.auctionseats.domain.show.entity.ShowsInfo;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ShowsInfoRepository extends JpaRepository<ShowsInfo, Long> {
    // 공연 정보 카테고리별 페이징 페이징 조회 categoryName null일시 전체 조회
    @Query("select g from ShowsInfo g where (:categoryName is null or g.showsCategory.name = :categoryName)")
    Slice<ShowsInfo> findAllByCategoryName(Pageable pageable, @Param("categoryName") String categoryName);
}