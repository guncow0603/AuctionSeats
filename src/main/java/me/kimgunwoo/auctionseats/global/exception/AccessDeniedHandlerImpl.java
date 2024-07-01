package me.kimgunwoo.auctionseats.global.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import me.kimgunwoo.auctionseats.global.response.ApiResponse;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static me.kimgunwoo.auctionseats.global.exception.ErrorCode.REQUIRED_ADMIN_USER_AUTHORITY;

@Component
@RequiredArgsConstructor
public class AccessDeniedHandlerImpl implements AccessDeniedHandler {

    private final ObjectMapper mapper;

    @Override
    public void handle(
            HttpServletRequest request,
            HttpServletResponse response,
            AccessDeniedException accessDeniedException
    ) throws IOException, ServletException {

        String result = mapper.writeValueAsString(
                ApiResponse.of(
                        REQUIRED_ADMIN_USER_AUTHORITY.getCode(),
                        REQUIRED_ADMIN_USER_AUTHORITY.getMessage(),
                        "{}"
                )
        );

        response.setStatus(REQUIRED_ADMIN_USER_AUTHORITY.getHttpStatus().value());

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());

        response.getWriter().write(result);
    }
}