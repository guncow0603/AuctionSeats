package me.kimgunwoo.auctionseats.domain.grade.service;

import me.kimgunwoo.auctionseats.domain.admin.dto.request.GradeRequest;
import me.kimgunwoo.auctionseats.domain.show.entity.Shows;

public interface GradeService {
    // 등급 생성
    void createGrade(GradeRequest gradeRequest, Shows shows);
}
