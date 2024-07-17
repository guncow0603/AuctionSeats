package me.kimgunwoo.auctionseats.domain.grade.service;

import me.kimgunwoo.auctionseats.domain.admin.dto.request.ZoneGradeCreateRequest;
import me.kimgunwoo.auctionseats.domain.grade.entity.Grade;
import me.kimgunwoo.auctionseats.domain.grade.entity.ZoneGrade;
import me.kimgunwoo.auctionseats.domain.place.entity.Zone;

public interface ZoneGradeService {

    // 구역 등급 생성
    ZoneGrade createZoneGrade(ZoneGradeCreateRequest zoneGradeCreateRequest, Zone zone, Grade grade);


    /**
     * ZoneGrade를 가져올 때, 조건에 따라 zone과 grade를 fetch join한다.
     * @param id
     * @param fetchZone
     * @param fetchGrade
     * @return ZoneGrade
     */
    ZoneGrade findZoneGradeWithFetch(Long id, boolean fetchZone, boolean fetchGrade);
}
