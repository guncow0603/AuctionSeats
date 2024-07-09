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
}
