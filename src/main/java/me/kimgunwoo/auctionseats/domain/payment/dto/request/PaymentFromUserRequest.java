package me.kimgunwoo.auctionseats.domain.payment.dto.request;


import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.kimgunwoo.auctionseats.domain.payment.entity.Payment;
import me.kimgunwoo.auctionseats.domain.user.entity.User;

import java.util.UUID;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PaymentFromUserRequest {

    @NotNull
    private Long amount;

    @NotNull
    private String orderName;

    @Builder
    public PaymentFromUserRequest(Long amount, String orderName) {
        this.amount = amount;
        this.orderName = orderName;
    }

    public Payment toEntity(User user) {
        return Payment.builder()
                .user(user)
                .amount(amount)
                .orderName(orderName)
                .orderId(UUID.randomUUID().toString())
                .build();
    }
}