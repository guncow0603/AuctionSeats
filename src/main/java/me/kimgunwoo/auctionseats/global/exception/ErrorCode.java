package me.kimgunwoo.auctionseats.global.exception;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    /* USER */
    EXISTED_USER_EMAIL(HttpStatus.CONFLICT, "U10000", "중복된 이메일 입니다."),

    EXISTED_USER_NICKNAME(HttpStatus.CONFLICT, "U10001", "사용 중인 닉네임 입니다."),
    NOT_FOUND_USER(HttpStatus.NOT_FOUND, "U10001", "존재하지 않는 회원입니다."),

    INVALID_TOKEN(HttpStatus.BAD_REQUEST, "U10100", "잘못된 토큰입니다."),
    NOT_FOUND_USER_FOR_LOGIN(HttpStatus.NOT_FOUND, "U10101", "존재하지 않는 회원입니다."),
    REQUIRED_LOGIN(HttpStatus.UNAUTHORIZED, "U10102", "로그인이 필요합니다."),






    /* SHOWS */









    /* PLACE */









    /* AUCTION */









    /* RESERVATION */









    /* GLOBAL */
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "", ""),
    INTERNAL_BAD_REQUEST(HttpStatus.BAD_REQUEST, "", "");
    private HttpStatus httpStatus;
    private String code;
    private String message;
}
