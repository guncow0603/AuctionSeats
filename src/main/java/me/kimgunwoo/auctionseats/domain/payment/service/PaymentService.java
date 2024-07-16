package me.kimgunwoo.auctionseats.domain.payment.service;


import me.kimgunwoo.auctionseats.domain.payment.dto.request.PaymentFromUserRequest;
import me.kimgunwoo.auctionseats.domain.payment.dto.request.PaymentToApiRequest;
import me.kimgunwoo.auctionseats.domain.payment.dto.response.PaymentSuccessResponse;
import me.kimgunwoo.auctionseats.domain.user.entity.User;

public interface PaymentService {
    PaymentToApiRequest requestPayment(User user, PaymentFromUserRequest request);

    PaymentSuccessResponse successPayment(String jsonBody);

    String getKey();
}