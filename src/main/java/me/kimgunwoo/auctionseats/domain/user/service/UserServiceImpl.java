package me.kimgunwoo.auctionseats.domain.user.service;

import lombok.RequiredArgsConstructor;
import me.kimgunwoo.auctionseats.domain.user.dto.request.UserCreateRequest;
import me.kimgunwoo.auctionseats.domain.user.dto.request.UserNicknameUpdateRequest;
import me.kimgunwoo.auctionseats.domain.user.dto.request.UserPhoneUpdateRequest;
import me.kimgunwoo.auctionseats.domain.user.entity.User;
import me.kimgunwoo.auctionseats.domain.user.repository.UserRepository;
import me.kimgunwoo.auctionseats.global.exception.ApiException;
import me.kimgunwoo.auctionseats.global.exception.ErrorCode;
import me.kimgunwoo.auctionseats.global.util.LettuceUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static me.kimgunwoo.auctionseats.global.exception.ErrorCode.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final LettuceUtils lettuceUtils;

    @Transactional
    @Override
    public void signup(UserCreateRequest request) {
        String email = request.getEmail();
        String nickname = request.getNickname();
        /* 이메일 중복 검사 */
        if (userRepository.existsByEmailAndIsDeletedIsFalse(email)) {
            throw new ApiException(ErrorCode.EXISTED_USER_EMAIL);
        }

        /* 닉네임 중복 검사 */
        checkNickname(nickname);

        User user = request.toEntity(passwordEncoder);
        userRepository.save(user);
    }

    @Override
    public boolean isExistedPhoneNumber(String phoneNumber) {
        return userRepository.existsByPhoneNumberAndIsDeletedIsFalse(phoneNumber);
    }

    @Override
    public User findByUserId(Long userId) {
        return userRepository.findByIdAndIsDeletedIsFalse(userId)
                .orElseThrow(() -> new ApiException(NOT_FOUND_BY_ID));
    }

    @Transactional
    @Override
    public void updateUserNicknameInfo(User loginUser, Long userId, UserNicknameUpdateRequest request) {
        User user = checkAndGetUser(loginUser, userId);
        checkNickname(request.nickname());
        user.updateUserNickName(request.nickname());
    }

    @Transactional
    @Override
    public void updateUserPhoneInfo(User loginUser, Long userId, UserPhoneUpdateRequest request) {
        User user = checkAndGetUser(loginUser, userId);
        checkPhoneVerificationCode(request.phoneNumber(), request.verificationNumber());
        user.updatePhoneNumber(request.phoneNumber());
    }

    private void checkPhoneVerificationCode(String phoneNumber, String verificationCode) {
        /* 핸드폰 번호 인증 번호 검사 */
        if (!lettuceUtils.hasKey("[Verification]" + phoneNumber)) {
            throw new ApiException(EXCEED_VERIFICATION_TIME);
        }

        if (!lettuceUtils.get("[Verification]" + phoneNumber).equals(verificationCode)) {
            throw new ApiException(INVALID_VERIFICATION_NUMBER);
        }
    }

    private User checkAndGetUser(User loginUser, Long userId) {
        if (!loginUser.getId().equals(userId)) {
            throw new ApiException(ACCESS_DENIED);
        }
        return findByUserId(userId);
    }

    private void checkNickname(String nickname) {
        if (userRepository.existsByNicknameAndIsDeletedIsFalse(nickname)) {
            throw new ApiException(ErrorCode.EXISTED_USER_NICKNAME);
        }
    }
}