package me.kimgunwoo.auctionseats.domain.user.dto.request;

import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class UserDeleteRequest {

    private String password;

}