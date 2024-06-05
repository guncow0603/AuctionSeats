package me.kimgunwoo.auctionseats.domain.user.service;

import me.kimgunwoo.auctionseats.domain.user.dto.request.UserCreateRequest;

public interface UserService {
    void signup(UserCreateRequest request);
}
