package me.kimgunwoo.auctionseats.domain.reservation.controller;

import lombok.RequiredArgsConstructor;
import me.kimgunwoo.auctionseats.domain.reservation.dto.response.ReservationDetailResponse;
import me.kimgunwoo.auctionseats.domain.reservation.service.ReservationService;
import me.kimgunwoo.auctionseats.domain.user.entity.User;
import me.kimgunwoo.auctionseats.global.annotaion.CurrentUser;
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

    @PostMapping
    public ResponseEntity<ApiResponse<ReservationDetailResponse>> reserve(
            @RequestBody ReservationCreateRequest request,
            @CurrentUser User user
    ) {
        ReservationDetailResponse response = reservationService.reserve(user, request);

        return ResponseEntity
                .status(SUCCESS_RESERVE.getHttpStatus())
                .body(
                        ApiResponse.of(
                                SUCCESS_RESERVE.getCode(),
                                SUCCESS_RESERVE.getMessage(),
                                response)
                );
    }

    @GetMapping("/{reservationId}")
    public ResponseEntity<ApiResponse<ReservationDetailResponse>> getReservationDetail(
            @PathVariable Long reservationId,
            @CurrentUser User user
    ) {

        ReservationDetailResponse response =
                reservationService.getReservationDetailResponse(user, reservationId);

        return ResponseEntity
                .status(SUCCESS_SEARCH_RESERVATION.getHttpStatus())
                .body(
                        ApiResponse.of(
                                SUCCESS_SEARCH_RESERVATION.getCode(),
                                SUCCESS_SEARCH_RESERVATION.getMessage(),
                                response
                        )
                );
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<ReservationResponse>>> searchReservations(
            @CurrentUser User user,
            @RequestParam Integer page,
            @RequestParam Integer size
    ) {
        List<ReservationResponse> response = reservationService.searchReservations(user, page, size);

        return ResponseEntity
                .status(SUCCESS_SEARCH_RESERVATIONS.getHttpStatus())
                .body(
                        ApiResponse.of(
                                SUCCESS_SEARCH_RESERVATIONS.getCode(),
                                SUCCESS_SEARCH_RESERVATIONS.getMessage(),
                                response
                        )
                );
    }

    @DeleteMapping("/{reservationId}")
    public ResponseEntity<ApiResponse<EmptyObject>> cancelReservation(
            @PathVariable Long reservationId,
            @CurrentUser User user
    ) {
        reservationService.cancelReservation(user, reservationId);

        return ResponseEntity
                .status(SUCCESS_CANCEL_RESERVATION.getHttpStatus())
                .body(
                        ApiResponse.of(
                                SUCCESS_CANCEL_RESERVATION.getCode(),
                                SUCCESS_CANCEL_RESERVATION.getMessage()
                        )
                );
    }

    @PostMapping("/{reservationId}/qrcode")
    public ResponseEntity<ApiResponse<String>> createQRCode(
            @PathVariable Long reservationId,
            @CurrentUser User user
    ) {
        String qrCode = reservationService.createQRCode(user, reservationId);

        return ResponseEntity
                .status(SUCCESS_CREATE_RESERVATION_AUTHENTICATION_QRCODE.getHttpStatus())
                .body(
                        ApiResponse.of(
                                SUCCESS_CREATE_RESERVATION_AUTHENTICATION_QRCODE.getCode(),
                                SUCCESS_CREATE_RESERVATION_AUTHENTICATION_QRCODE.getMessage(),
                                qrCode
                        )
                );
    }

    @PostMapping("/{reservationId}/auth")
    public ResponseEntity<ApiResponse<EmptyObject>> authenticateReservation(
            @PathVariable Long reservationId,
            @RequestParam String authCode
    ) {
        reservationService.authenticateReservation(reservationId, authCode);

        return ResponseEntity
                .status(SUCCESS_AUTHENTICATE_RESERVATION.getHttpStatus())
                .body(
                        ApiResponse.of(
                                SUCCESS_AUTHENTICATE_RESERVATION.getCode(),
                                SUCCESS_AUTHENTICATE_RESERVATION.getMessage()
                        )
                );
    }
}