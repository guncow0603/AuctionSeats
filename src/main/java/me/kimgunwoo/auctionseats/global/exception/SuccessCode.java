package me.kimgunwoo.auctionseats.global.exception;
import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SuccessCode {

    /* USER */
    SUCCESS_USER_SIGN_UP(HttpStatus.CREATED, "U00000", "회원 가입에 성공했습니다."),

    SUCCESS_USER_LOGIN(HttpStatus.OK, "U00100", "로그인에 성공했습니다."),







    /* SHOWS */









    /* PLACE */









    /* AUCTION */









    /* RESERVATION */









    /* GLOBAL */
    OK(HttpStatus.OK, "", "성공");
    private HttpStatus httpStatus;
    private String code;
    private String message;
}
