package me.kimgunwoo.auctionseats.domain.user.dto.response;

public record UserResponse(
        Long id,
        String email,
        String name,
        String nickname,
        String phoneNumber,
        Long point
) {}
