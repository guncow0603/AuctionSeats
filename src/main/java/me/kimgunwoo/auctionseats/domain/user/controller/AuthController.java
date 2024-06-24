package me.kimgunwoo.auctionseats.domain.user.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import me.kimgunwoo.auctionseats.domain.user.service.AuthService;
import me.kimgunwoo.auctionseats.global.response.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static me.kimgunwoo.auctionseats.global.exception.SuccessCode.SUCCESS_USER_LOGOUT;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/logout")
    public ResponseEntity<ApiResponse> logout(HttpServletRequest request) {
        authService.logout(request);
        return ResponseEntity.status(SUCCESS_USER_LOGOUT.getHttpStatus())
                .body(ApiResponse.of(SUCCESS_USER_LOGOUT.getCode(), SUCCESS_USER_LOGOUT.getMessage()));
    }

}