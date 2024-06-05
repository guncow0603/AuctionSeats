package me.kimgunwoo.auctionseats.domain.user.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record UserInfoUpdateRequest(
        @NotBlank @Pattern(regexp = "^[가-힣]+$") String nickname,
        @NotBlank @Pattern(regexp = "^[0-9]{2,3}-[0-9]{3,4}-[0-9]{4}$") String phoneNumber
) {}
