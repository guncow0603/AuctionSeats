package me.kimgunwoo.auctionseats.domain.show.repository;

import io.lettuce.core.dynamic.annotation.Param;
import me.kimgunwoo.auctionseats.domain.show.entity.Shows;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ShowsRepository extends JpaRepository<Shows, Long>, ShowsRepositoryCustom {
    @Query("select g from Shows g "
            + "left join fetch g.showsInfo gi "
            + "left join fetch gi.showsCategory gc "
            + "where (:categoryName is null or gc.name = :categoryName) AND (g.endDate > now())")
    Slice<Shows> findAllByShowsAndCategoryName(Pageable pageable, @Param("categoryName") String categoryName);
}
