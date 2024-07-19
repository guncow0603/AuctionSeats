package me.kimgunwoo.auctionseats.domain.user.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UserUpdateRequest(
        String nickname,
        String phoneNumber
){
}
