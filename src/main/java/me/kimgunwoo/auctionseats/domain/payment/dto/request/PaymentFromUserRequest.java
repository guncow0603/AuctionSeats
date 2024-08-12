package me.kimgunwoo.auctionseats.domain.payment.dto.request;


import jakarta.validation.constraints.NotNull;
import me.kimgunwoo.auctionseats.domain.payment.entity.Payment;
import me.kimgunwoo.auctionseats.domain.user.entity.User;

import java.util.UUID;

public record PaymentFromUserRequest(
        @NotNull Long amount,
        @NotNull String orderName
) {
    public Payment toEntity(User user) {
        return Payment.builder()
                .user(user)
                .amount(amount)
                .orderName(orderName)
                .orderId(UUID.randomUUID().toString())
                .build();
    }
}
