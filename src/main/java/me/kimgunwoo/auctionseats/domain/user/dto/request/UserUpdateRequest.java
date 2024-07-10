package me.kimgunwoo.auctionseats.domain.user.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UserUpdateRequest(
        @Pattern(regexp = "^[가-힣]+$", message = "한글로만 입력해주세요.")
        @Size(max = 10, message = "최소 2자, 최대 10자로 입력해주세요.")
        String nickname,
        @Pattern(regexp = "^01([0|1|6|7|8|9])?([0-9]{3,4})?([0-9]{4})$", message = "숫자로만 입력해주세요.")
        String phoneNumber,
        String verificationNumber
){
}
