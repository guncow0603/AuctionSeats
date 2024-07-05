package me.kimgunwoo.auctionseats.domain.user.service;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface AuthService {

    /*
     * 로그아웃
     *
     * @param HttpServletRequest
     * @return void
     * */
    void logout(HttpServletRequest request);

    /*
     * 토큰 재바급
     *
     * @param HttpServletRequest
     * @param HttpServletResponse
     * */
    void reissue(HttpServletRequest request, HttpServletResponse response);
}