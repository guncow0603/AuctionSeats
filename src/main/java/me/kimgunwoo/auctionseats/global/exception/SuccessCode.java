package me.kimgunwoo.auctionseats.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

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
    SUCCESS_GET_ALL_SHOWS_INFO(HttpStatus.OK, "S00000", "공연 정보 전체 조회 성공했습니다."),
    SUCCESS_GET_SLICE_SHOWS(HttpStatus.OK, "S00100", "공연 페이징 조회 성공했습니다."),
    SUCCESS_GET_SHOWS(HttpStatus.OK, "S00200", "공연 단건 조회 성공했습니다."),

    /* PLACE */
    SUCCESS_GET_ALL_PLACE(HttpStatus.OK, "P00000", "공연장 전체 조회 성공했습니다."),

    /* GRADE */
    SUCCESS_GET_ALL_GRADE(HttpStatus.OK, "F00000", "등급 전체 조회를 성공했습니다."),

    /* ZONE */
    SUCCESS_GET_ALL_ZONE(HttpStatus.OK, "Z00000", "구역 전체 조회 성공했습니다."),

    /* SCHEDULE */
    SUCCESS_GET_ALL_SCHEDULE(HttpStatus.OK, "S00000", "전 회차 조회 성공했습니다."),
    
    /* TOKEN */
    SUCCESS_REISSUE_TOKEN(HttpStatus.CREATED, "T00000", "토큰 재발급에 성공했습니다."),
    
    /* AUCTION */
    SUCCESS_GET_AUCTION_INFO(HttpStatus.OK, "A00500", "경매 정보 조회에 성공했습니다."),

    /* BID */
    SUCCESS_GET_ALL_BID(HttpStatus.OK, "B00000", "입찰 내역 조회에 성공했습니다."),
    SUCCESS_BID(HttpStatus.CREATED, "B00100", "입찰에 성공했습니다."),

    /* ADMIN */
    SUCCESS_SHOWS_AND_SCHEDULE_CREATE(HttpStatus.CREATED, "M09900", "공연 및 회차 생성을 성공했습니다."),
    SUCCESS_PLACE_AND_ZONE_CREATE(HttpStatus.CREATED, "M09901", "공연장 및 구역 생성을 성공했습니다."),
    SUCCESS_GRADE_CREATE(HttpStatus.CREATED, "M09902", "등급 생성 성공했습니다."),
    SUCCESS_ZONE_GRADE_CREATE(HttpStatus.CREATED, "M09903", "구역 등급 생성 성공했습니다."),
    SUCCESS_AUCTION_CREATE(HttpStatus.CREATED, "M09904", "경매 생성 성공했습니다."),
    SUCCESS_SHOWS_INFO_CREATE(HttpStatus.CREATED, "M09905", "공연정보 생성 성공했습니다."),

    /* RESERVATION */
    SUCCESS_RESERVE(HttpStatus.CREATED, "R00000", "예매 성공했습니다."),
    SUCCESS_SEARCH_RESERVATION(HttpStatus.OK, "R00001", "예매 조회 성공"),
    SUCCESS_SEARCH_RESERVATIONS(HttpStatus.OK, "R00002", "예매 목록 조회 성공"),
    SUCCESS_CANCEL_RESERVATION(HttpStatus.OK, "R00003", "예매 취소 성공"),
    SUCCESS_CREATE_RESERVATION_AUTHENTICATION_QRCODE(HttpStatus.OK, "R00004", "예매 인증 QR코드 생성 성공"),
    SUCCESS_AUTHENTICATE_RESERVATION(HttpStatus.OK, "R00005", "예매 인증 성공"),

    /* GLOBAL */
    OK(HttpStatus.OK, "", "성공");
    private HttpStatus httpStatus;
    private String code;
    private String message;
}
