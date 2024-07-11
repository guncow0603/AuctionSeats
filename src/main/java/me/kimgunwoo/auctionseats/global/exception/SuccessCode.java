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
    SUCCESS_USER_LOGOUT(HttpStatus.OK, "U00200", "로그아웃에 성공했습니다"),
    SUCCESS_UPDATE_USER_INFO(HttpStatus.OK, "U00400", "회원 정보 수정에 성공했습니다."),
    SUCCESS_UPDATE_USER_NICKNAME(HttpStatus.OK, "U00400", "닉네임 변경에 성공했습니다."),
    SUCCESS_UPDATE_USER_PHONE(HttpStatus.OK, "U00401", "전화 번호 변경에 성공했습니다."),
    SUCCESS_GET_USER_INFO(HttpStatus.OK, "U00500", "회원 정보 조회에 성공했습니다."),
    SUCCESS_DELETE_USER(HttpStatus.OK, "U00600", "회원 탈퇴에 성공했습니다."),
    SUCCESS_UPDATE_USER_PASSWORD(HttpStatus.OK, "U00401", "비밀 번호 변경에 성공했습니다."),

    /* SHOWS */


    /* PLACE */


    /* TOKEN */
    SUCCESS_REISSUE_TOKEN(HttpStatus.CREATED, "T00000", "토큰 재발급에 성공했습니다."),




    /* AUCTION */
    SUCCESS_BID(HttpStatus.CREATED, "A00100", "입찰에 성공했습니다."),

    /* ADMIN */
    SUCCESS_SHOWS_AND_SCHEDULE_CREATE(HttpStatus.CREATED, "Z09900", "공연정보, 공연 및 회차가 추가되었습니다."),
    SUCCESS_PLACE_AND_ZONE_CREATE(HttpStatus.CREATED, "Z09901", "공연장 및 구역이 추가되었습니다."),
    SUCCESS_SHOWS_SEQUENCE_SEAT_AND_AUCTION_CREATE(HttpStatus.CREATED, "Z09902", "공연 회차 별 좌석"),

    /* RESERVATION */
    SUCCESS_RESERVE(HttpStatus.CREATED, "R00000", "예매 성공했습니다."),


    /* GLOBAL */
    OK(HttpStatus.OK, "", "성공");
    private HttpStatus httpStatus;
    private String code;
    private String message;
}
