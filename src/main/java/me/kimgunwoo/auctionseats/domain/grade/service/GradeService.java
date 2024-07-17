package me.kimgunwoo.auctionseats.domain.grade.service;

import me.kimgunwoo.auctionseats.domain.admin.dto.request.GradeCreateRequest;
import me.kimgunwoo.auctionseats.domain.grade.entity.Grade;
import me.kimgunwoo.auctionseats.domain.show.entity.Shows;

public interface GradeService {
    // 등급 생성
    Grade createGrade(GradeCreateRequest gradeCreateRequest, Shows shows);

    // 등급 프록시 객체 조회
    Grade getReferenceById(Long gradeId);
}
