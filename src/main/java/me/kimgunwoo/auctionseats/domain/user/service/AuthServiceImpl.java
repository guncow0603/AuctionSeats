package me.kimgunwoo.auctionseats.domain.user.service;

import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import me.kimgunwoo.auctionseats.global.exception.ApiException;
import me.kimgunwoo.auctionseats.global.exception.ErrorCode;
import me.kimgunwoo.auctionseats.global.jwt.JwtUtil;
import me.kimgunwoo.auctionseats.global.util.LettuceUtils;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private static final String PREFIX_REFRESH_TOKEN = "RefreshToken: ";
    private static final String PREFIX_LOGOUT = "Logout: ";
    private final JwtUtil jwtUtil;
    private final LettuceUtils lettuceUtils;

    @Override
    public void logout(HttpServletRequest request) {
        String accessToken = jwtUtil.getAccessTokenFromRequestHeader(request);

        try {
            jwtUtil.validateToken(accessToken);
        } catch (ApiException e) {
            throw new ApiException(ErrorCode.INVALID_TOKEN);
        }

        Claims claims = jwtUtil.getUserInfoFromToken(accessToken);
        String username = claims.getSubject();
        Date expiration = claims.getExpiration();
        Integer remainingTime = jwtUtil.getRemainingTime(expiration);

        lettuceUtils.delete(PREFIX_REFRESH_TOKEN + username);
        lettuceUtils.save(PREFIX_LOGOUT + username, accessToken, remainingTime);
    }
}