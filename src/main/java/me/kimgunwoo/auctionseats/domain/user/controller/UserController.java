package me.kimgunwoo.auctionseats.domain.user.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import me.kimgunwoo.auctionseats.domain.user.dto.request.UserCreateRequest;
import me.kimgunwoo.auctionseats.domain.user.dto.request.UserNicknameUpdateRequest;
import me.kimgunwoo.auctionseats.domain.user.dto.request.UserPhoneUpdateRequest;
import me.kimgunwoo.auctionseats.domain.user.entity.User;
import me.kimgunwoo.auctionseats.domain.user.service.UserService;
import me.kimgunwoo.auctionseats.global.annotaion.CurrentUser;
import me.kimgunwoo.auctionseats.global.exception.SuccessCode;
import me.kimgunwoo.auctionseats.global.response.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

import static me.kimgunwoo.auctionseats.global.exception.SuccessCode.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<ApiResponse> signup(@RequestBody @Valid UserCreateRequest request) {
        userService.signup(request);
        return ResponseEntity.status(SUCCESS_USER_SIGN_UP.getHttpStatus())
                .body(
                        ApiResponse.of(SUCCESS_USER_SIGN_UP.getCode(),
                                SUCCESS_USER_SIGN_UP.getMessage()
                        )
                );
    }

    @PutMapping("/{user_id}/nickname")
    public ResponseEntity<ApiResponse> updateUserNicknameInfo(
            @CurrentUser User user,
            @PathVariable Long user_id,
            @RequestBody @Valid UserNicknameUpdateRequest request
    ) {
        userService.updateUserNicknameInfo(user, user_id, request);
        return ResponseEntity.status(SUCCESS_UPDATE_USER_NICKNAME.getHttpStatus())
                .body(
                        ApiResponse.of(SUCCESS_UPDATE_USER_NICKNAME.getCode(),
                                SUCCESS_UPDATE_USER_NICKNAME.getMessage()
                        )
                );
    }

    @PutMapping("/{user_id}/phone")
    public ResponseEntity<ApiResponse> updateUserPhoneInfo(
            @CurrentUser User user,
            @PathVariable Long user_id,
            @RequestBody @Valid UserPhoneUpdateRequest request
    ) {
        userService.updateUserPhoneInfo(user, user_id, request);
        return ResponseEntity.status(SUCCESS_UPDATE_USER_PHONE.getHttpStatus())
                .body(
                        ApiResponse.of(SUCCESS_UPDATE_USER_PHONE.getCode(),
                                SUCCESS_UPDATE_USER_PHONE.getMessage()
                        )
                );
    }

    @GetMapping("/status")
    public ResponseEntity<Map<String, Object>> getUserStatus(@CurrentUser User user) {
        Map<String, Object> status = new HashMap<>();

        if (user != null) {
            // 사용자가 인증되어 있을 때
            status.put("isLoggedIn", true);
            status.put("user", user); // 사용자 아이디 등을 추가할 수 있습니다.
            // 다른 필요한 사용자 정보도 추가할 수 있습니다.
        } else {
            // 사용자가 인증되어 있지 않을 때
            status.put("isLoggedIn", false);
        }

        return ResponseEntity.ok(status);
    }
}
