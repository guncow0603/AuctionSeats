package me.kimgunwoo.auctionseats.global.jwt;


import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SecurityException;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import me.kimgunwoo.auctionseats.domain.user.entity.constant.Role;
import me.kimgunwoo.auctionseats.global.exception.ApiException;
import me.kimgunwoo.auctionseats.global.exception.SuccessCode;
import me.kimgunwoo.auctionseats.global.response.ApiResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Base64;
import java.util.Date;

import static me.kimgunwoo.auctionseats.global.exception.ErrorCode.*;

@Slf4j(topic = "JwtUtil")
@Component
public class JwtUtil {

    public static final String ACCESS_TOKEN_HEADER = "Authorization";
    public static final String REFRESH_TOKEN_HEADER = "RefreshToken";
    public static final String AUTHORIZATION_KEY = "auth";
    public static final String BEARER_PREFIX = "Bearer ";
    private final Long ACCESS_TOKEN_TIME = 60 * 60 * 1000L; // 60분
    private final Long REFRESH_TOKEN_TIME = 30 * 24 * 60 * 60 * 1000L; // 한 달
    private final ObjectMapper mapper = new ObjectMapper();

    @Value("${jwt.secret.key}")
    private String secretKey;
    private Key key;
    private JwtParser jwtParser;

    @PostConstruct
    public void init() {
        byte[] bytes = Base64.getDecoder().decode(secretKey);
        key = Keys.hmacShaKeyFor(bytes);
        jwtParser = Jwts.parserBuilder().setSigningKey(key).build();
    }

    /* 엑세스 토큰 생성 */
    public String createAccessToken(Long id, String username, Role role) {
        Date now = new Date();

        return BEARER_PREFIX +
                Jwts.builder()
                        .setSubject(username)
                        .claim(AUTHORIZATION_KEY, role)
                        .claim("identify", id)
                        .setExpiration(new Date(now.getTime() + ACCESS_TOKEN_TIME))
                        .setIssuedAt(now)
                        .signWith(key, SignatureAlgorithm.HS256)
                        .compact();
    }

    /* 리프레시 토큰 생성 */
    public String createRefreshToken(String username, Role role) {
        Date now = new Date();

        return BEARER_PREFIX +
                Jwts.builder()
                        .setSubject(username)
                        .claim(AUTHORIZATION_KEY, role)
                        .setExpiration(new Date(now.getTime() + REFRESH_TOKEN_TIME))
                        .setIssuedAt(now)
                        .signWith(key, SignatureAlgorithm.HS256)
                        .compact();
    }

    /* JWT 토큰 substring */
    public String substringToken(String token) {
        if (StringUtils.hasText(token) && token.startsWith(BEARER_PREFIX)) {
            return token.substring(7);
        }

        throw new ApiException(INVALID_TOKEN);
    }

    /* 토큰 검증 */
    public void validateToken(String token) {
        try {
            jwtParser.parseClaimsJws(token);
        } catch (SecurityException | MalformedJwtException e) {
            throw new ApiException(INVALID_JWT_TOKEN);
        } catch (ExpiredJwtException e) {
            throw new ApiException(EXPIRED_JWT_TOKEN);
        } catch (UnsupportedJwtException e) {
            throw new ApiException(UNSUPPORTED_JWT_TOKEN);
        } catch (IllegalArgumentException e) {
            throw new ApiException(NON_ILLEGAL_ARGUMENT_JWT_TOKEN);
        }
    }

    /* 토큰에서 사용자 정보 가져오기 */
    public Claims getUserInfoFromToken(String token) {
        return jwtParser.parseClaimsJws(token).getBody();
    }

    public String resolveAccessToken(HttpServletRequest request) {
        String accessToken = request.getHeader(ACCESS_TOKEN_HEADER);
        if (StringUtils.hasText(accessToken) && accessToken.startsWith(BEARER_PREFIX)) {
            return accessToken.substring(7);
        }
        return null;
    }

    public String resolveRefreshToken(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();

        if (cookies == null) {
            return null;
        }

        String refreshToken = "";
        for (Cookie cookie : cookies) {
            refreshToken = URLDecoder.decode(cookie.getValue(), StandardCharsets.UTF_8);
        }
        return substringToken(refreshToken);
    }

    public Cookie setCookieWithRefreshToken(String refreshToken) {
        refreshToken = URLEncoder.encode(refreshToken, StandardCharsets.UTF_8)
                .replaceAll("\\+", "%20");

        Cookie cookie = new Cookie(JwtUtil.REFRESH_TOKEN_HEADER, refreshToken);
        cookie.setSecure(true);
        cookie.setHttpOnly(true);
        cookie.setPath("/");

        return cookie;
    }

    public String getAccessTokenFromRequestHeader(HttpServletRequest request) {
        String accessToken = request.getHeader(ACCESS_TOKEN_HEADER);
        if (!StringUtils.hasText(accessToken)) {
            return null;
        }
        return substringToken(accessToken);
    }

    public Integer getRemainingTime(Date expiration) {
        Date now = new Date();
        return Math.toIntExact((expiration.getTime() - now.getTime()) / 60 / 1000);
    }

    public void setExceptionResponse(HttpServletResponse response, ApiException apiException) throws IOException {
        response.setStatus(apiException.getHttpStatus().value());

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());

        String result = mapper.writeValueAsString(
                ApiResponse.of(apiException.getCode(), apiException.getMessage(), "{}")
        );

        response.getWriter().write(result);
    }

    public void setSuccessResponse(HttpServletResponse response, SuccessCode successCode) throws IOException {
        response.setStatus(successCode.getHttpStatus().value());

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());

        String result = mapper.writeValueAsString(
                ApiResponse.of(successCode.getCode(), successCode.getMessage(), "{}")
        );

        response.getWriter().write(result);
    }
}
