package me.kimgunwoo.auctionseats.domain.user.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import me.kimgunwoo.auctionseats.domain.user.enums.Role;
import me.kimgunwoo.auctionseats.global.jwt.JwtUtil;
import me.kimgunwoo.auctionseats.global.util.LettuceUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.util.ReflectionTestUtils;

import static me.kimgunwoo.auctionseats.domain.user.util.UserUtil.TEST_EMAIL;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.verify;

@DisplayName("회원 서비스 테스트")
@ExtendWith(MockitoExtension.class)
class AuthServiceImplTest {
    private final String secretKey = "a9asfjjasfkjd12mfk31f12dsadas==123ASdsadq1ds3da";
    @Mock
    JwtUtil jwtUtil;
    @Mock
    UserService userService;
    @InjectMocks
    private AuthServiceImpl sut;
    @Mock
    private LettuceUtils lettuceUtils;

    @BeforeEach
    void beforeEach() {
        ReflectionTestUtils.setField(jwtUtil, "secretKey", secretKey);
        jwtUtil.init();
    }

    @Test
    void 로그아웃_테스트() {
        // Given
        String email = "tester@gmail.com";
        Role role = Role.USER;

        String accessToken = "access-token";
        String refreshToken = "refresh-token";

        MockHttpServletRequest request = new MockHttpServletRequest();

        given(jwtUtil.getAccessTokenFromRequestHeader(any())).willReturn(accessToken);

        Claims claims = Jwts.claims().setSubject(email);
        claims.put("auth", role);
        claims.put("identify", 1);

        given(jwtUtil.getUserInfoFromToken(any())).willReturn(claims);
        given(jwtUtil.getRemainingTime(any())).willReturn(60);

        // When
        sut.logout(request);

        // Then
        then(lettuceUtils).should().delete(any(String.class));
        then(lettuceUtils).should().save(any(), any(), anyLong());
    }


    @Nested
    class 토큰_재발급_테스트 {

        @Test
        void 성공() {
            // Given
            MockHttpServletRequest request = new MockHttpServletRequest();
            MockHttpServletResponse response = new MockHttpServletResponse();

            String refreshToken = "test-refresh-token";
            String newAccessToken = "new-access-token";
            String newRefreshToken = "new-refresh-token";

            Claims claims = Jwts.claims().setSubject(TEST_EMAIL);
            claims.put("auth", Role.USER);
            claims.put("identify", "1");

            given(jwtUtil.resolveRefreshToken(any())).willReturn(refreshToken);
            given(jwtUtil.getUserInfoFromToken(any())).willReturn(claims);
            given(lettuceUtils.get(any())).willReturn(refreshToken);
            given(jwtUtil.createAccessToken(any(), any(), any(), any())).willReturn(newAccessToken);
            given(jwtUtil.createRefreshToken(any(), any(), any(), any())).willReturn(newRefreshToken);

            // When
            sut.reissue(request, response);

            // Then
            verify(jwtUtil).resolveRefreshToken(any());
            verify(jwtUtil).getUserInfoFromToken(any());
            verify(lettuceUtils).get(any());
            verify(jwtUtil).createAccessToken(any(), any(), any(), any());
            verify(jwtUtil).createRefreshToken(any(), any(), any(), any());
        }
    }
}