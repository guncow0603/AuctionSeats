package me.kimgunwoo.auctionseats.domain.user.service;

import me.kimgunwoo.auctionseats.domain.user.dto.request.UserCreateRequest;
import me.kimgunwoo.auctionseats.domain.user.dto.request.UserPasswordUpdateRequest;
import me.kimgunwoo.auctionseats.domain.user.dto.request.UserUpdateRequest;
import me.kimgunwoo.auctionseats.domain.user.dto.response.UserResponse;
import me.kimgunwoo.auctionseats.domain.user.entity.User;

public interface UserService {
    /*
     * 회원 가입 메서드
     *
     * @param UserCreateRequest 회원 가입 시, 입력 정보
     * @return void
     * */
    UserResponse signup(UserCreateRequest request);
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
     * 유저 정보 수정 - 입력이 되고, 검증된 값에 한하여 수정됨
     *
     * @param user 		로그인 한 유저 정보
     * @param userId 	정보가 수정될 유저의 id
     * @param request	변경할 정보
     * */
    UserResponse updateUserInfo(User loginUser, Long userId, UserUpdateRequest request);
    /*
     * 유저 정보 조회
     *
     * @param user	 		로그인 한 융저 정보
     * @param userId 		조회할 유저의 id
     *
     * @return UserResponse	유저 응답 정보 dto
     * */
    UserResponse getUserInfo(User user, Long userId);
    /*
     * 유저 비밀 번호 수정 - 수정할 비밀 번호와 확인 비밀 번호 입력 값에 대한 일치 여부는 프론트 단에서 검증
     *
     * @param user 		로그인 한 유저 정보
     * @param userId  	비밀 번호를 수정할 유저의 id
     * @param request	변경할 비밀 번호 - 이미  검증됨
     * */
    void updateUserPassword(User user, Long userId, UserPasswordUpdateRequest request);

    /*
     * 회원 탈퇴
     *
     * @param user 		로그인 한 유저 정보
     * @param userId  	탈퇴할 유저의 id
     * */
    void deleteUser(User user, Long userId);
}