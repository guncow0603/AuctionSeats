package me.kimgunwoo.auctionseats.domain.user.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import me.kimgunwoo.auctionseats.domain.user.entity.User;
import me.kimgunwoo.auctionseats.domain.user.service.AuthService;
import me.kimgunwoo.auctionseats.global.annotaion.CurrentUser;
import me.kimgunwoo.auctionseats.global.response.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

import static me.kimgunwoo.auctionseats.global.exception.SuccessCode.SUCCESS_REISSUE_TOKEN;
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

    @PostMapping("/reissue")
    public ResponseEntity<ApiResponse> reissue(HttpServletRequest request, HttpServletResponse response) {
        authService.reissue(request, response);
        return ResponseEntity.status(SUCCESS_REISSUE_TOKEN.getHttpStatus())
                .body(ApiResponse.of(
                        SUCCESS_REISSUE_TOKEN.getCode(),
                        SUCCESS_REISSUE_TOKEN.getMessage()
                ));
    }

    @GetMapping("/status")
    public ResponseEntity<Map<String, Object>> getUserStatus(@CurrentUser User user) {
        Map<String, Object> status = new HashMap<>();

        if (user != null) {
            // 사용자가 인증되어 있을 때
            Long point = authService.findPoint(user);
            status.put("isLoggedIn", true);
            status.put("user", user); // 사용자 아이디 등을 추가할 수 있습니다.
            status.put("point", point);
            // 다른 필요한 사용자 정보도 추가할 수 있습니다.
        } else {
            // 사용자가 인증되어 있지 않을 때
            status.put("isLoggedIn", false);
        }

        return ResponseEntity.ok(status);
    }
}