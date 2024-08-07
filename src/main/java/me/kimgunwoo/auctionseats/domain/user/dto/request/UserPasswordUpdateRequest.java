package me.kimgunwoo.auctionseats.domain.user.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record UserPasswordUpdateRequest(
        @NotBlank(message = "필수 입력입니다.")
        @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*[!@#$%^*+=-])(?=.*[0-9]).$",
                message = "영어, 숫자, 특수문자(!,@,#,$,%,^) 조합으로 입력해주세요.")
        @Size (min = 8, max = 15, message = "최소 8자, 최대 15자로 입력해주세요.")
        String password,
        String confirmPassword
) {}
