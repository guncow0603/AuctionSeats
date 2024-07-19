package me.kimgunwoo.auctionseats.domain.grade.service;

import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import me.kimgunwoo.auctionseats.domain.admin.dto.request.GradeCreateRequest;
import me.kimgunwoo.auctionseats.domain.grade.dto.GradeGetResponse;
import me.kimgunwoo.auctionseats.domain.grade.entity.Grade;
import me.kimgunwoo.auctionseats.domain.grade.repository.GradeRepository;
import me.kimgunwoo.auctionseats.domain.show.entity.Shows;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GradeServiceImpl implements GradeService {

    private final GradeRepository gradeRepository;

    // 등급 생성
    @Override
    public Grade createGrade(GradeCreateRequest gradeCreateRequest, Shows shows) {
        Grade grade = gradeCreateRequest.toEntity(shows);
        return gradeRepository.save(grade);
    }

    // 해당 공현의 등급 전체 정보 조회
    @Override
    @Transactional(readOnly = true)
    public List<GradeGetResponse> getAllGrade(Long showsId) {
        List<Grade> gradeList = gradeRepository.findAllByShowsId(showsId);
        return gradeList.stream().map(GradeGetResponse::new).toList();
    }

    // 등급 프록시 객체 조회
    @Override
    public Grade getReferenceById(Long gradeId) {
        return gradeRepository.getReferenceById(gradeId);
    }
}
