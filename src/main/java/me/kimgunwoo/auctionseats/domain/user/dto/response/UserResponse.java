package me.kimgunwoo.auctionseats.domain.user.dto.response;

import lombok.Builder;
import me.kimgunwoo.auctionseats.domain.user.entity.User;

import java.time.LocalDate;
@Builder
public record UserResponse(
         Long id,

         String email,

         String name,

         String nickname,

         String phoneNumber,

         LocalDate birth,

         Long point

) {
    public static UserResponse from(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .name(user.getName())
                .nickname(user.getNickname())
                .phoneNumber(user.getPhoneNumber())
                .birth(user.getBirth())
                .point(user.getPoint())
                .build();
    }
}
