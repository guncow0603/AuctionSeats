package me.kimgunwoo.auctionseats.domain.grade.service;

import lombok.RequiredArgsConstructor;
import me.kimgunwoo.auctionseats.domain.admin.dto.request.ZoneGradeRequest;
import me.kimgunwoo.auctionseats.domain.grade.entity.Grade;
import me.kimgunwoo.auctionseats.domain.grade.entity.ZoneGrade;
import me.kimgunwoo.auctionseats.domain.grade.repository.ZoneGradeRepository;
import me.kimgunwoo.auctionseats.domain.place.entity.Zone;
import me.kimgunwoo.auctionseats.global.exception.ApiException;
import me.kimgunwoo.auctionseats.global.exception.ErrorCode;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ZoneGradeServiceImpl implements ZoneGradeService {

    private final ZoneGradeRepository zoneGradeRepository;

    // 구역 등급 생성
    @Override
    public ZoneGrade createZoneGrade(ZoneGradeRequest zoneGradeRequest, Zone zone, Grade grade) {
        ZoneGrade zoneGrade = zoneGradeRequest.toEntity(zone, grade);
        return zoneGradeRepository.save(zoneGrade);
    }

    @Override
    public ZoneGrade findZoneGradeWithFetch(Long id, boolean fetchZone, boolean fetchGrade) {
        return zoneGradeRepository.findZoneGrade(id, fetchZone, fetchGrade)
                .orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND_ZONE_GRADE));
    }
}