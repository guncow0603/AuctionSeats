package me.kimgunwoo.auctionseats.domain.grade.repository;

import me.kimgunwoo.auctionseats.domain.grade.entity.ZoneGrade;

import java.util.Optional;

public interface ZoneGradeQueryRepository {

    /**
     * ZoneGrade를 가져올 때, 조건에 따라 zone과 grade를 fetch join한다.
     * @param id
     * @param fetchZone
     * @param fetchGrade
     * @return ZoneGrade
     */
    Optional<ZoneGrade> findZoneGrade(Long id, boolean fetchZone, boolean fetchGrade);
}
