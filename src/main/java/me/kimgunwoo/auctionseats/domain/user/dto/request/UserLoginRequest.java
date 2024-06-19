package me.kimgunwoo.auctionseats.domain.user.dto.request;

import lombok.*;


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserLoginRequest {

    private String email;
    private String password;

    @Builder
    public UserLoginRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }
}

