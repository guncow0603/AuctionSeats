package me.kimgunwoo.auctionseats.domain.user.service;

import me.kimgunwoo.auctionseats.domain.user.dto.request.UserCreateRequest;
import me.kimgunwoo.auctionseats.domain.user.dto.request.UserNicknameUpdateRequest;
import me.kimgunwoo.auctionseats.domain.user.dto.request.UserPhoneUpdateRequest;
import me.kimgunwoo.auctionseats.domain.user.entity.User;

public interface UserService {
    /*
     * 회원 가입 메서드
     *
     * @param UserCreateRequest 회원 가입 시, 입력 정보
     * @return void
     * */
    void signup(UserCreateRequest request);

    /*
     * 휴대폰 번호 중복 검사 메서드
     *
     * @param String phoneNumber
     * @return boolean
     * */
    boolean isExistedPhoneNumber(String phoneNumber);


    /*
     * 해당 id 값의 유저 찾기 메서드
     *
     * @param userId 찾을 유저의 id
     * @return User  해당 id를 가진 유저 객체
     * */
    User findByUserId(Long userId);

    /*
     * 유저 닉네임 정보 수정
     *
     * @param user 		로그인한 유저 정보
     * @param userId 	정보가 수정될 유저의 id
     * @param request	변경할 닉네임 정보
     * */
    void updateUserNicknameInfo(User user, Long userId, UserNicknameUpdateRequest request);

    /*
     * 유저 전화 번호 변경
     *
     * @param user 		로그인한 유저 정보
     * @param userId 	정보가 수정될 유저의 id
     * @param request	변경할 전화 번호 정보
     * */
    void updateUserPhoneInfo(User user, Long userId, UserPhoneUpdateRequest request);
}
