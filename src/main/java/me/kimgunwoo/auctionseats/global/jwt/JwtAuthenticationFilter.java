package me.kimgunwoo.auctionseats.global.jwt;


import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.kimgunwoo.auctionseats.domain.user.dto.request.UserLoginRequest;
import me.kimgunwoo.auctionseats.domain.user.entity.User;
import me.kimgunwoo.auctionseats.domain.user.entity.constant.Role;
import me.kimgunwoo.auctionseats.global.exception.ApiException;
import me.kimgunwoo.auctionseats.global.response.ApiResponse;
import me.kimgunwoo.auctionseats.global.security.UserDetailsImpl;
import me.kimgunwoo.auctionseats.global.util.LettuceUtils;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static me.kimgunwoo.auctionseats.global.exception.ErrorCode.INTERNAL_SERVER_ERROR;
import static me.kimgunwoo.auctionseats.global.exception.ErrorCode.NOT_FOUND_USER_FOR_LOGIN;
import static me.kimgunwoo.auctionseats.global.exception.SuccessCode.SUCCESS_USER_LOGIN;

@Slf4j(topic = "JWT 토큰 인가")
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final JwtUtil jwtUtil;
    private final LettuceUtils lettuceUtils;
    private final ObjectMapper mapper = new ObjectMapper();
    @PostConstruct
    public void setup() {
        setFilterProcessesUrl("/api/v1/auth/login");
    }
    @Override
    public Authentication attemptAuthentication(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws AuthenticationException {
        try {
            UserLoginRequest req =
                    mapper.readValue(request.getInputStream(), UserLoginRequest.class);

            return getAuthenticationManager().authenticate(
                    new UsernamePasswordAuthenticationToken(
                            req.email(),
                            req.password(),
                            null
                    )
            );
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new ApiException(INTERNAL_SERVER_ERROR);
        }
    }
    @Override
    protected void successfulAuthentication(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain chain,
            Authentication authResult
    ) throws ServletException, IOException {
        log.info("Login Success, username : {}", authResult.getName());
        User user = ((UserDetailsImpl)authResult.getPrincipal()).getUser();
        Role role = ((UserDetailsImpl)authResult.getPrincipal()).getUser().getRole();
        String username = user.getEmail();
        Long id = user.getId();

        String accessToken = jwtUtil.createAccessToken(id, username, role);
        String refreshToken = jwtUtil.createRefreshToken(id, username, role);

        lettuceUtils.save(
                JwtUtil.REFRESH_TOKEN_HEADER + " " + username,
                jwtUtil.substringToken(refreshToken),
                JwtUtil.REFRESH_TOKEN_TIME
        );


        jwtUtil.setAccessTokenInHeader(response, accessToken);
        jwtUtil.setRefreshTokenInCookie(response, refreshToken);

        response.setStatus(SUCCESS_USER_LOGIN.getHttpStatus().value());

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        String result = mapper.writeValueAsString(

                ApiResponse.of(SUCCESS_USER_LOGIN.getCode(), SUCCESS_USER_LOGIN.getMessage(), "{}")
        );

        response.getWriter().write(result);
    }
    @Override
    protected void unsuccessfulAuthentication(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException failed
    ) throws IOException, ServletException {
        log.info("Login Fail, msg : {}", failed.getMessage());

        throw new ApiException(NOT_FOUND_USER_FOR_LOGIN);

    }
}