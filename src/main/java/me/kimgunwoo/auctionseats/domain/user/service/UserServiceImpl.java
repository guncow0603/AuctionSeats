package me.kimgunwoo.auctionseats.domain.user.service;

import lombok.RequiredArgsConstructor;
import me.kimgunwoo.auctionseats.domain.user.dto.request.UserCreateRequest;
import me.kimgunwoo.auctionseats.domain.user.dto.request.UserPasswordUpdateRequest;
import me.kimgunwoo.auctionseats.domain.user.dto.request.UserUpdateRequest;
import me.kimgunwoo.auctionseats.domain.user.dto.response.UserResponse;
import me.kimgunwoo.auctionseats.domain.user.entity.User;
import me.kimgunwoo.auctionseats.domain.user.repository.UserRepository;
import me.kimgunwoo.auctionseats.global.exception.ApiException;
import me.kimgunwoo.auctionseats.global.exception.ErrorCode;
import me.kimgunwoo.auctionseats.global.util.LettuceUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

import static me.kimgunwoo.auctionseats.global.exception.ErrorCode.*;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final LettuceUtils lettuceUtils;

    @Transactional
    @Override
    public UserResponse signup(UserCreateRequest request) {
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

        return UserResponse.from(user);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean isExistedPhoneNumber(String phoneNumber) {
        return userRepository.existsByPhoneNumberAndIsDeletedIsFalse(phoneNumber);

    }

    @Override
    @Transactional(readOnly = true)
    public User findByUserId(Long userId) {
        return userRepository.findByIdAndIsDeletedIsFalse(userId)
                .orElseThrow(() -> new ApiException(NOT_FOUND_BY_ID));
    }

    @Transactional
    @Override
    public UserResponse updateUserInfo(User loginUser, Long userId, UserUpdateRequest request) {
        User user = checkAndGetUser(loginUser, userId);

        if (!Objects.requireNonNullElse(request.nickname(), "").isBlank()) {
            checkNickname(request.nickname());
            user.updateUserNickName(request.nickname());
        }

        return UserResponse.from(user);
    }

    @Override
    @Transactional(readOnly = true)
    public UserResponse getUserInfo(User loginUser, Long userId) {
        User user = checkAndGetUser(loginUser, userId);

        return UserResponse.from(user);
    }

    @Override
    @Transactional
    public void updateUserPassword(User loginUser, Long userId, UserPasswordUpdateRequest request) {
        User user = checkAndGetUser(loginUser, userId);

        checkPassword(user, request.password());
        user.updatePassword(passwordEncoder.encode(request.password()));
    }

    @Override
    @Transactional
    public void deleteUser(User loginUser, Long userId) {
        User user = checkAndGetUser(loginUser, userId);

        user.delete();
    }

    @Override
    @Transactional(readOnly = true)
    public Long findUserPoint(Long userId) {
        return userRepository.findPointById(userId);
    }

    private void checkPassword(User user, String password) {
        if (passwordEncoder.matches(password, user.getPassword())) {
            throw new ApiException(ALREADY_USED_PASSWORD);
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