package me.kimgunwoo.auctionseats.domain.user.entity.constant;


import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum Role {
    USER("ROLE_USER"),
    ADMIN("ROLE_ADMIN");

    private final String authority;
}