package me.kimgunwoo.auctionseats.domain.user.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import me.kimgunwoo.auctionseats.domain.user.dto.request.UserCreateRequest;
import me.kimgunwoo.auctionseats.domain.user.dto.request.UserPasswordUpdateRequest;
import me.kimgunwoo.auctionseats.domain.user.dto.request.UserUpdateRequest;
import me.kimgunwoo.auctionseats.domain.user.dto.response.UserResponse;
import me.kimgunwoo.auctionseats.domain.user.entity.User;
import me.kimgunwoo.auctionseats.domain.user.service.UserService;
import me.kimgunwoo.auctionseats.global.annotaion.CurrentUser;
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
        UserResponse response = userService.signup(request);
        return ResponseEntity.status(SUCCESS_USER_SIGN_UP.getHttpStatus())
                .body(
                        ApiResponse.of(
                                SUCCESS_USER_SIGN_UP.getCode(),
                                SUCCESS_USER_SIGN_UP.getMessage(),
                                response
                        )
                );
    }
    @PutMapping("/{userId}")
    public ResponseEntity<ApiResponse> updateUserInfo(
            @CurrentUser User user,
            @PathVariable Long userId,
            @RequestBody @Valid UserUpdateRequest request
    ) {
        UserResponse response = userService.updateUserInfo(user, userId, request);
        return ResponseEntity.status(SUCCESS_UPDATE_USER_INFO.getHttpStatus())
                .body(
                        ApiResponse.of(
                                SUCCESS_UPDATE_USER_INFO.getCode(),
                                SUCCESS_UPDATE_USER_INFO.getMessage(),
                                response
                        )
                );
    }
    @GetMapping("/{userId}")
    public ResponseEntity<ApiResponse> getUserInfo(
            @CurrentUser User user,
            @PathVariable Long userId
    ) {
        UserResponse response = userService.getUserInfo(user, userId);
        return ResponseEntity.status(SUCCESS_GET_USER_INFO.getHttpStatus())
                .body(
                        ApiResponse.of(
                                SUCCESS_GET_USER_INFO.getCode(),
                                SUCCESS_GET_USER_INFO.getMessage(),
                                response
                        )
                );
    }
    @PatchMapping("/{userId}")
    public ResponseEntity<ApiResponse> updateUserPassword(
            @CurrentUser User user,
            @PathVariable Long userId,
            @RequestBody @Valid UserPasswordUpdateRequest request
    ) {
        userService.updateUserPassword(user, userId, request);
        return ResponseEntity.status(SUCCESS_UPDATE_USER_PASSWORD.getHttpStatus())
                .body(
                        ApiResponse.of(
                                SUCCESS_UPDATE_USER_PASSWORD.getCode(),
                                SUCCESS_UPDATE_USER_PASSWORD.getMessage()
                        )
                );
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<ApiResponse> deleteUser(
            @CurrentUser User user,
            @PathVariable Long userId
    ) {
        userService.deleteUser(user, userId);
        return ResponseEntity.status(SUCCESS_DELETE_USER.getHttpStatus())
                .body(
                        ApiResponse.of(
                                SUCCESS_DELETE_USER.getCode(),
                                SUCCESS_DELETE_USER.getMessage()
                        )
                );
    }
}