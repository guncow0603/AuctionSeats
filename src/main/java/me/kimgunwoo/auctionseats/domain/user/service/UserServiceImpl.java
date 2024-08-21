package me.kimgunwoo.auctionseats.domain.user.service;

import lombok.RequiredArgsConstructor;
import me.kimgunwoo.auctionseats.domain.user.dto.request.UserCreateRequest;
import me.kimgunwoo.auctionseats.domain.user.dto.request.UserDeleteRequest;
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

import java.util.regex.Pattern;

import static me.kimgunwoo.auctionseats.global.exception.ErrorCode.*;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final LettuceUtils lettuceUtils;  // LettuceUtils 주입

    @Transactional
    @Override
    public UserResponse signup(UserCreateRequest request) {
        String email = request.getEmail();
        String nickname = request.getNickname();

        // Redis에서 이메일 중복 확인
        if (lettuceUtils.hasKey("email:" + email)) {
            throw new ApiException(ErrorCode.EXISTED_USER_EMAIL);
        }

        // 이메일 중복 검사 (DB)
        if (userRepository.existsByEmailAndIsDeletedIsFalse(email)) {
            throw new ApiException(ErrorCode.EXISTED_USER_EMAIL);
        }

        // Redis에서 닉네임 중복 확인
        if (lettuceUtils.hasKey("nickname:" + nickname)) {
            throw new ApiException(ErrorCode.EXISTED_USER_NICKNAME);
        }

        // 닉네임 중복 검사 (DB)
        checkNickname(nickname);

        User user = request.toEntity(passwordEncoder);
        userRepository.save(user);

        // 회원가입 성공 시 Redis에 이메일과 닉네임 캐싱 (중복 체크용)
        lettuceUtils.save("email:" + email, "1", 3600000); // 1시간 캐싱
        lettuceUtils.save("nickname:" + nickname, "1", 3600000); // 1시간 캐싱

        return UserResponse.from(user);
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

        if (!request.nickname().isBlank()) {
            if (request.nickname().length() < 2 || request.nickname().length() > 10) {
                throw new ApiException(INVALID_NICKNAME_LENGTH);
            }
            String nicknameRegexp = "^[가-힣]+$";
            if (!Pattern.matches(nicknameRegexp, request.nickname())) {
                throw new ApiException(INVALID_NICKNAME_PATTERN);
            }
            checkNickname(request.nickname());
            user.updateUserNickName(request.nickname());

            // 닉네임 변경 시, 새로운 닉네임을 Redis에 캐싱
            lettuceUtils.save("nickname:" + request.nickname(), "1", 3600000); // 1시간 캐싱
        }

        if (!request.phoneNumber().isBlank()) {
            String phoneRegexp = "^01([0|1|6|7|8|9])?([0-9]{3,4})?([0-9]{4})$";
            if (!Pattern.matches(phoneRegexp, request.phoneNumber())) {
                throw new ApiException(INVALID_PHONE_NUMBER_PATTERN);
            }
            user.updatePhoneNumber(request.phoneNumber());
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
    public void deleteUser(User loginUser, UserDeleteRequest request) {
        User user = findByUserId(loginUser.getId());
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new ApiException(NOT_MATCH_PASSWORD);
        }
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

