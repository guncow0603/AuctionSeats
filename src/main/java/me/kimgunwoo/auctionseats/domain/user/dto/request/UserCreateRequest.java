package me.kimgunwoo.auctionseats.domain.user.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import me.kimgunwoo.auctionseats.domain.user.entity.User;
import me.kimgunwoo.auctionseats.domain.user.enums.Role;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Getter
@RequiredArgsConstructor
public class UserCreateRequest {

        @NotBlank(message = "필수 입력입니다.")
        @Email(message = "이메일 형식으로 입력해주세요.")
        private final String email;

        @Pattern(
                regexp = "^(?=.*?[a-zA-Z])(?=.*?[0-9])(?=.*?[#!@$ %^])[a-zA-Z0-9@#$%^!]*$",
                message = "영어, 숫자, 특수문자(!,@,#,$,%,^) 조합으로 입력해주세요."
        )
        @Size(min = 8, max = 15, message = "최소 8자, 최대 15자로 입력해주세요.")
        private final String password;

        @Pattern(regexp = "^[가-힣]+$", message = "한글로만 입력해주세요.")
        @Size(min = 2, max = 10, message = "최소 2자, 최대 10자로 입력해주세요.")
        private final String name;

        @Pattern(regexp = "^[가-힣]+$", message = "한글로만 입력해주세요.")
        @Size(min = 2, max = 10, message = "최소 2자, 최대 10자로 입력해주세요.")
        private final String nickname;

        @NotBlank(message = "필수 입력입니다.")
        @Pattern(regexp = "^01([0|1|6|7|8|9])?([0-9]{3,4})?([0-9]{4})$", message = "숫자로만 입력해주세요.")
        private final String phoneNumber;

        @NotNull(message = "필수 입력입니다.")
        @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$", message = "형식에 맞춰 입력해주세요.")
        // @JsonFormat(pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
        private final String birth;

        public User toEntity(PasswordEncoder encoder) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                LocalDate localDate = LocalDate.parse(birth, formatter);

                return User.builder()
                        .email(email)
                        .password(encoder.encode(password))
                        .name(name)
                        .nickname(nickname)
                        .phoneNumber(phoneNumber)
                        .birth(localDate)
                        .role(Role.USER)
                        .build();
        }

}