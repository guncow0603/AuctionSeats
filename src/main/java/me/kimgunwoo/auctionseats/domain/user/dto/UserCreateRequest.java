package me.kimgunwoo.auctionseats.domain.user.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record UserCreateRequest(
        @NotBlank @Email String email,
        @NotBlank @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*[!@#$%^*+=-])(?=.*[0-9]).{8,15}$") String password,
        @NotBlank @Pattern(regexp = "^[가-힣]+$") String name,
        @NotBlank @Pattern(regexp = "^[가-힣]+$") String nickname,
        @NotBlank @Pattern(regexp = "^[0-9]{2,3}-[0-9]{3,4}-[0-9]{4}$") String phoneNumber
) {}
