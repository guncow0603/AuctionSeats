package me.kimgunwoo.auctionseats.domain.user.service;

import static me.kimgunwoo.auctionseats.domain.user.UserUtil.*;
import me.kimgunwoo.auctionseats.domain.user.dto.request.UserCreateRequest;
import me.kimgunwoo.auctionseats.domain.user.entity.User;
import me.kimgunwoo.auctionseats.domain.user.repository.UserRepository;
import me.kimgunwoo.auctionseats.global.exception.ApiException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;

import static me.kimgunwoo.auctionseats.global.exception.ErrorCode.EXISTED_USER_EMAIL;
import static me.kimgunwoo.auctionseats.global.exception.ErrorCode.EXISTED_USER_NICKNAME;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
@DisplayName("User Service Test")
@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    UserRepository userRepository;
    @Mock
    PasswordEncoder passwordEncoder;
    @InjectMocks
    UserServiceImpl sut;
    @Captor
    ArgumentCaptor<User> argumentCaptor;

    @Nested
    @DisplayName("회원 가입 검증 테스트")
    class signupTest {

        @Test
        @DisplayName("실패 - 중복 이메일")
        void givenExistedEmail_fail() {
            // Given
            UserCreateRequest request = getUserCreateRequest(
                    EMAIL,
                    PASSWORD,
                    NAME,
                    NICKNAME,
                    PHONE_NUMBER,
                    BIRTH
            );

            given(userRepository.existsByEmailAndIsDeletedIsFalse(request.email())).willReturn(true);

            // When
            ApiException exception = assertThrows(
                    ApiException.class,
                    () -> sut.signup(request)
            );

            // Then
            assertEquals(EXISTED_USER_EMAIL.getMessage(), exception.getMessage());
        }

        @Test
        @DisplayName("실패 - 중복 닉네임")
        void givenExistedNickname_fail() {
            // Given
            UserCreateRequest request = getUserCreateRequest(
                    EMAIL,
                    PASSWORD,
                    NAME,
                    NICKNAME,
                    PHONE_NUMBER,
                    BIRTH
            );

            given(userRepository.existsByEmailAndIsDeletedIsFalse(request.email())).willReturn(false);
            given(userRepository.existsByNicknameAndIsDeletedIsFalse(request.nickname())).willReturn(true);

            // When
            ApiException exception = assertThrows(
                    ApiException.class,
                    () -> sut.signup(request)
            );

            // Then
            assertEquals(EXISTED_USER_NICKNAME.getMessage(), exception.getMessage());
        }

        @Test
        @DisplayName("회원 가입 성공")
        void success() {
            // Given
            UserCreateRequest request = getUserCreateRequest(
                    EMAIL,
                    PASSWORD,
                    NAME,
                    NICKNAME,
                    PHONE_NUMBER,
                    BIRTH
            );

            User user = request.toEntity(passwordEncoder);

            given(userRepository.existsByEmailAndIsDeletedIsFalse(request.email())).willReturn(false);
            given(userRepository.existsByNicknameAndIsDeletedIsFalse(request.nickname())).willReturn(false);
            given(userRepository.save(any(User.class))).willReturn(user);

            // When
            sut.signup(request);

            // Then
            verify(userRepository).existsByEmailAndIsDeletedIsFalse(request.email());
            verify(userRepository).existsByNicknameAndIsDeletedIsFalse(request.nickname());
            verify(userRepository).save(any(User.class));

            verify(userRepository).save(argumentCaptor.capture());
            assertEquals(request.nickname(), argumentCaptor.getValue().getNickname());
        }

    }
}