package me.kimgunwoo.auctionseats.domain.user.service;

import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import me.kimgunwoo.auctionseats.domain.user.entity.User;
import me.kimgunwoo.auctionseats.domain.user.enums.Role;
import me.kimgunwoo.auctionseats.global.exception.ApiException;
import me.kimgunwoo.auctionseats.global.jwt.JwtUtil;
import me.kimgunwoo.auctionseats.global.util.LettuceUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

import static me.kimgunwoo.auctionseats.global.exception.ErrorCode.INVALID_JWT_TOKEN;
import static me.kimgunwoo.auctionseats.global.jwt.JwtUtil.REFRESH_TOKEN_HEADER;
import static me.kimgunwoo.auctionseats.global.jwt.JwtUtil.REFRESH_TOKEN_TIME;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private static final String PREFIX_REFRESH_TOKEN = "RefreshToken: ";
    private static final String PREFIX_LOGOUT = "Logout: ";
    private final JwtUtil jwtUtil;
    private final LettuceUtils lettuceUtils;
    private final UserService userService;

    @Override
    @Transactional
    public void logout(HttpServletRequest request) {
        String accessToken = jwtUtil.getAccessTokenFromRequestHeader(request);

        jwtUtil.validateToken(accessToken);

        Claims claims = jwtUtil.getUserInfoFromToken(accessToken);
        String username = claims.getSubject();
        Date expiration = claims.getExpiration();
        Integer remainingTime = jwtUtil.getRemainingTime(expiration);

        lettuceUtils.delete(PREFIX_REFRESH_TOKEN + username);
        lettuceUtils.save(PREFIX_LOGOUT + username, accessToken, remainingTime);
    }

    @Override
    @Transactional
    public void reissue(HttpServletRequest request, HttpServletResponse response) {
        String refreshToken = jwtUtil.resolveRefreshToken(request);
        // 토큰 검증
        jwtUtil.validateToken(refreshToken);
        // 유저 정보 추출
        Claims claims = jwtUtil.getUserInfoFromToken(refreshToken);
        String username = claims.getSubject();
        Role role = Role.valueOf(String.valueOf(claims.get("auth")));
        Long id = Long.parseLong(String.valueOf(claims.get("identify")));
        String nickname = String.valueOf(claims.get("nickname"));

        if (!lettuceUtils.get(REFRESH_TOKEN_HEADER + " " + username).equals(refreshToken)) {
            throw new ApiException(INVALID_JWT_TOKEN);
        }

        String newAccessToken = jwtUtil.createAccessToken(id, username, role, nickname);
        String newRefreshToken = jwtUtil.createRefreshToken(id, username, role, nickname);

        jwtUtil.setAccessTokenInHeader(response, newAccessToken);
        jwtUtil.setRefreshTokenInCookie(response, newRefreshToken);
        lettuceUtils.delete(REFRESH_TOKEN_HEADER + " " + username);
        lettuceUtils.save(
                REFRESH_TOKEN_HEADER + " " + username,
                jwtUtil.substringToken(newRefreshToken),
                REFRESH_TOKEN_TIME
        );
    }

    @Override
    public Long findPoint(User user) {
        return userService.findByUserId(user.getId()).getPoint();
    }
}