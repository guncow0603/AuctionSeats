package me.kimgunwoo.auctionseats.domain.grade.Controller;

import lombok.RequiredArgsConstructor;
import me.kimgunwoo.auctionseats.domain.grade.dto.response.GradeGetResponse;
import me.kimgunwoo.auctionseats.domain.grade.service.GradeService;
import me.kimgunwoo.auctionseats.domain.grade.service.ZoneGradeService;
import me.kimgunwoo.auctionseats.global.response.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static me.kimgunwoo.auctionseats.global.exception.SuccessCode.SUCCESS_GET_ALL_GRADE;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class GradeController {
    private final GradeService gradeService;
    private final ZoneGradeService zoneGradeService;

    // 해당 공현의 등급 전체 정보 조회
    @GetMapping("/shows/{showsId}/grade")
    public ResponseEntity<ApiResponse<List<GradeGetResponse>>> getAllGrade(@PathVariable Long showsId) {
        List<GradeGetResponse> gradeGetResponses = gradeService.getAllGrade(showsId);
        return ResponseEntity
                .status(
                        SUCCESS_GET_ALL_GRADE.getHttpStatus())
                .body(
                        ApiResponse.of(
                                SUCCESS_GET_ALL_GRADE.getCode(),
                                SUCCESS_GET_ALL_GRADE.getMessage(),
                                gradeGetResponses)
                );
    }
}