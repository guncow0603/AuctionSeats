package me.kimgunwoo.auctionseats.domain.grade.service;

import me.kimgunwoo.auctionseats.domain.admin.dto.request.ZoneGradeRequest;
import me.kimgunwoo.auctionseats.domain.grade.entity.Grade;
import me.kimgunwoo.auctionseats.domain.grade.entity.ZoneGrade;
import me.kimgunwoo.auctionseats.domain.place.entity.Zone;

public interface ZoneGradeService {

    // 구역 등급 생성
    ZoneGrade createZoneGrade(ZoneGradeRequest zoneGradeRequest, Zone zone, Grade grade);
}
