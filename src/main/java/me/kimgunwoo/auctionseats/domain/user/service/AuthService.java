package me.kimgunwoo.auctionseats.domain.user.service;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import me.kimgunwoo.auctionseats.domain.user.entity.User;

public interface AuthService {

    /*
     * 로그아웃
     *
     * @param HttpServletRequest
     * @return void
     * */
    void logout(HttpServletRequest request);

    /*
     * 토큰 재빌급
     *
     * @param HttpServletRequest
     * @param HttpServletResponse
     * */
    void reissue(HttpServletRequest request, HttpServletResponse response);

    /*
     * 프론트에서 유저 상태 요청 API 호출 시, 포인트 정보도 함께 보내주기 위한 메서드
     *
     * @param User 로그인 유저 정보
     *
     * @return Long 현재 유저의 포인트 값
     * */
    Long findPoint(User user);
}