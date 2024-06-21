package me.kimgunwoo.auctionseats.domain.reservation.controller;

import lombok.RequiredArgsConstructor;
import me.kimgunwoo.auctionseats.domain.reservation.dto.request.ReservationCreateRequest;
import me.kimgunwoo.auctionseats.domain.reservation.dto.response.ReservationDetailResponse;
import me.kimgunwoo.auctionseats.domain.reservation.service.ReservationService;
import me.kimgunwoo.auctionseats.domain.user.entity.User;
import me.kimgunwoo.auctionseats.global.exception.ApiException;
import me.kimgunwoo.auctionseats.global.exception.ErrorCode;
import me.kimgunwoo.auctionseats.global.response.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.web.bind.annotation.*;

import static me.kimgunwoo.auctionseats.global.exception.SuccessCode.SUCCESS_RESERVE;


@RestController
@RequestMapping("/api/v1/reservations")
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;

    @PostMapping("/{sequenceId}/{seatId}")
    public ResponseEntity<ApiResponse<ReservationDetailResponse>> reserve(
            @PathVariable Long sequenceId,
            @PathVariable Long seatId,
            @RequestBody ReservationCreateRequest request,
            User user // TODO: 유저 주입해야 함
    ) {
        try {
            ReservationDetailResponse response =
                    reservationService.reserve(user, seatId, sequenceId, request);

            return ResponseEntity.ok(
                    ApiResponse.of(SUCCESS_RESERVE.getCode(), SUCCESS_RESERVE.getMessage(), response)
            );
        } catch (ObjectOptimisticLockingFailureException e) {
            throw new ApiException(ErrorCode.ALREADY_RESERVED_SEAT);
        }
    }
}
