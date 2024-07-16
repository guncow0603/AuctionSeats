package me.kimgunwoo.auctionseats.domain.show.repository;

import io.lettuce.core.dynamic.annotation.Param;
import me.kimgunwoo.auctionseats.domain.show.entity.ShowsInfo;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ShowsInfoRepository extends JpaRepository<ShowsInfo, Long> {
    @Query("select g from ShowsInfo g where g.showsCategory.name = :categoryName")
    Slice<ShowsInfo> findByCategoryName(Pageable pageable, @Param("categoryName") String categoryName);
}