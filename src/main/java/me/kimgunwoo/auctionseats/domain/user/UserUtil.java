package me.kimgunwoo.auctionseats.domain.user;

import me.kimgunwoo.auctionseats.domain.user.dto.request.UserCreateRequest;

import java.time.LocalDate;

public class UserUtil {

    public static UserCreateRequest getUserCreateRequest(
            String email,
            String password,
            String name,
            String nickname,
            String phoneNumber,
            LocalDate birth
    ) {
        return new UserCreateRequest(
                email,
                password,
                name,
                nickname,
                phoneNumber,
                birth
        );
    }

}
