package me.kimgunwoo.auctionseats.domain.user.service;

import java.util.Optional;

import me.kimgunwoo.auctionseats.domain.user.dto.request.UserCreateRequest;
import me.kimgunwoo.auctionseats.domain.user.entity.User;
import me.kimgunwoo.auctionseats.domain.user.repository.UserRepository;
import me.kimgunwoo.auctionseats.global.exception.ApiException;
import me.kimgunwoo.auctionseats.global.exception.ErrorCode;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void signup(UserCreateRequest request) {
        String email = request.email();
        String nickname = request.nickname();

        /* 이메일 중복 검사 */
        if (userRepository.existsByEmailAndIsDeletedIsFalse(email)) {
            throw new ApiException(ErrorCode.EXISTED_USER_EMAIL);
        }

        /* 닉네임 중복 검사 */
        if (userRepository.existsByNicknameAndIsDeletedIsFalse(nickname)) {
            throw new ApiException(ErrorCode.EXISTED_USER_NICKNAME);
        }

        User user = request.toEntity(passwordEncoder);
        userRepository.save(user);
    }
}
