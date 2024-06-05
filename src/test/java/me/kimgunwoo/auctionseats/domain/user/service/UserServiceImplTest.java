package me.kimgunwoo.auctionseats.domain.user.service;

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
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    private static final String EMAIL = "tester@gmail.com";
    private static final String PASSWORD = "test123!@#";
    private static final String NAME = "김수한";
    private static final String NICKNAME = "두루미";
    private static final String PHONE_NUMBER = "010-1234-5678";
    private static final LocalDate BIRTH = LocalDate.of(1990, 1, 1);
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
            UserCreateRequest request = new UserCreateRequest(
                    EMAIL,
                    PASSWORD,
                    NAME,
                    NICKNAME,
                    PHONE_NUMBER,
                    BIRTH
            );

            given(userRepository.existsByEmail(request.email())).willReturn(true);

            // When
            ApiException exception = assertThrows(
                    ApiException.class,
                    () -> sut.signup(request)
            );

            // Then
            assertEquals(EXISTED_USER_EMAIL.getMessage(), exception.getMessage());
        }

    }
}