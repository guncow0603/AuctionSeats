package me.kimgunwoo.auctionseats.domain.user.dto.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class UserLoginRequest {
    private final String email;
    private final String password;
}
