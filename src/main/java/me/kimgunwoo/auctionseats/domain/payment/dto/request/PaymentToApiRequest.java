package me.kimgunwoo.auctionseats.domain.payment.dto.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import me.kimgunwoo.auctionseats.domain.payment.entity.Payment;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class PaymentToApiRequest {

    private final Long amount;
    private final String orderName;
    private final String orderId;
    private final String userEmail;
    private final String userName;
    private final String successUrl;
    private final String failUrl;
    private final LocalDateTime createdAt;

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
