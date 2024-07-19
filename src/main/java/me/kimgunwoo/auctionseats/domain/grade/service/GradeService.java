package me.kimgunwoo.auctionseats.domain.grade.service;

import me.kimgunwoo.auctionseats.domain.admin.dto.request.GradeCreateRequest;
import me.kimgunwoo.auctionseats.domain.grade.dto.response.GradeGetResponse;
import me.kimgunwoo.auctionseats.domain.grade.entity.Grade;
import me.kimgunwoo.auctionseats.domain.show.entity.Shows;

import java.util.List;

public interface GradeService {
    // 등급 생성
    Grade createGrade(GradeCreateRequest gradeCreateRequest, Shows shows);

    // 해당 공현의 등급 전체 정보 조회
    List<GradeGetResponse> getAllGrade(Long showsId);
    
    // 등급 프록시 객체 조회
    Grade getReferenceById(Long gradeId);
}
