package me.kimgunwoo.auctionseats.domain.user.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record UserPhoneUpdateRequest (
        @NotBlank(message = "필수 입력입니다.")
        @Pattern(regexp = "^01([0|1|6|7|8|9])?([0-9]{3,4})?([0-9]{4})$", message = "숫자로만 입력해주세요.")
        String phoneNumber,

        @NotBlank(message = "필수 입력입니다.")
        String verificationNumber
){
}
