package me.kimgunwoo.auctionseats.domain.payment.dto.request;

import me.kimgunwoo.auctionseats.domain.payment.entity.Payment;

import java.time.LocalDateTime;

public record PaymentToApiRequest(
        Long amount,
        String orderName,
        String orderId,
        String userEmail,
        String userName,
        String successUrl,
        String failUrl,
        LocalDateTime createdAt
) {
    public static PaymentToApiRequest from(Payment payment, String successUrl, String failUrl) {
        return new PaymentToApiRequest(
                payment.getAmount(),
                payment.getOrderName(),
                payment.getOrderId(),
                payment.getUser().getEmail(),
                payment.getUser().getName(),
                successUrl,
                failUrl,
                payment.getCreatedAt()
        );
    }
}
