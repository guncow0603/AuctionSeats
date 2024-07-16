package me.kimgunwoo.auctionseats.domain.schedule.controller;

import lombok.RequiredArgsConstructor;
import me.kimgunwoo.auctionseats.domain.schedule.dto.response.ScheduleGetResponse;
import me.kimgunwoo.auctionseats.domain.schedule.service.ScheduleService;
import me.kimgunwoo.auctionseats.global.response.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static me.kimgunwoo.auctionseats.global.exception.SuccessCode.SUCCESS_GET_ALL_SCHEDULE;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class ScheduleController {

    private final ScheduleService scheduleService;

    // 해당 공연에 대한 전 회차 조회
    @GetMapping("/shows/{showsId}/schedules")
    public ResponseEntity<ApiResponse<List<ScheduleGetResponse>>> getAllSequence(@PathVariable Long showsId) {
        List<ScheduleGetResponse> scheduleGetResponses = scheduleService.getAllSchedule(showsId);

        return ResponseEntity
                .status(SUCCESS_GET_ALL_SCHEDULE.getHttpStatus())
                .body(
                        ApiResponse.of(
                                SUCCESS_GET_ALL_SCHEDULE.getCode(),
                                SUCCESS_GET_ALL_SCHEDULE.getMessage(),
                                scheduleGetResponses)
                );
    }

}