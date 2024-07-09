package me.kimgunwoo.auctionseats.domain.user.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UserNicknameUpdateRequest(
        @NotBlank(message = "필수 입력입니다.")
        @Pattern(regexp = "^[가-힣]+$", message = "한글로만 입력해주세요.")
        @Size(min = 2, max = 10, message = "최소 2자, 최대 10자로 입력해주세요.")
        String nickname
) {}
