package me.kimgunwoo.auctionseats.domain.user.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;


public record UserLoginRequest(
        @NotBlank(message = "필수 입력입니다.")
        String email,
        @NotBlank(message = "필수 입력입니다.")
        String password
) { @Builder
    public UserLoginRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }
}

