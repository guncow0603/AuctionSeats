package me.kimgunwoo.auctionseats.domain.user.controller;


import lombok.RequiredArgsConstructor;
import me.kimgunwoo.auctionseats.domain.user.entity.User;
import me.kimgunwoo.auctionseats.domain.user.service.PointService;
import me.kimgunwoo.auctionseats.global.annotaion.CurrentUser;
import me.kimgunwoo.auctionseats.global.response.ApiResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static me.kimgunwoo.auctionseats.global.exception.SuccessCode.SUCCESS_GET_CHARGE_POINT_LOG_LIST;
import static me.kimgunwoo.auctionseats.global.exception.SuccessCode.SUCCESS_GET_USE_POINT_LOG_LIST;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/points")
public class PointController {

    private PointService pointService;

    @GetMapping("/charge")
    public ResponseEntity<ApiResponse> getChargePointLogList(
            @CurrentUser User user,
            @PageableDefault(size = 15, sort = "createdAt", direction = Sort.Direction.DESC)
            Pageable pageable
    ) {
        var response = pointService.getChargePointLogList(user, pageable);
        return ResponseEntity.status(SUCCESS_GET_CHARGE_POINT_LOG_LIST.getHttpStatus())
                .body(
                        ApiResponse.of(
                                SUCCESS_GET_CHARGE_POINT_LOG_LIST.getCode(),
                                SUCCESS_GET_CHARGE_POINT_LOG_LIST.getMessage(),
                                response
                        )
                );
    }

    @GetMapping("/use")
    public ResponseEntity<ApiResponse> getUsePointLogList(
            @CurrentUser User user,
            @PageableDefault(size = 15, sort = "createdAt", direction = Sort.Direction.DESC)
            Pageable pageable
    ) {
        var response = pointService.getUsePointLogList(user, pageable);
        return ResponseEntity.status(SUCCESS_GET_USE_POINT_LOG_LIST.getHttpStatus())
                .body(
                        ApiResponse.of(
                                SUCCESS_GET_USE_POINT_LOG_LIST.getCode(),
                                SUCCESS_GET_USE_POINT_LOG_LIST.getMessage(),
                                response
                        )
                );
    }
}
