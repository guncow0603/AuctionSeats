package me.kimgunwoo.auctionseats.domain.grade.service;

import lombok.RequiredArgsConstructor;
import me.kimgunwoo.auctionseats.domain.admin.dto.request.GradeRequest;
import me.kimgunwoo.auctionseats.domain.grade.entity.Grade;
import me.kimgunwoo.auctionseats.domain.grade.repository.GradeRepository;
import me.kimgunwoo.auctionseats.domain.show.entity.Shows;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GradeServiceImpl implements GradeService {

    private final GradeRepository gradeRepository;

    // 등급 생성
    @Override
    public Grade createGrade(GradeRequest gradeRequest, Shows shows) {
        Grade grade = gradeRequest.toEntity(shows);
        return gradeRepository.save(grade);
    }

    // 등급 프록시 객체 탐색
    @Override
    public Grade getReferenceById(Long gradeId) {
        return gradeRepository.getReferenceById(gradeId);
    }
}
