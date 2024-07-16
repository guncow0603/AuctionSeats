package me.kimgunwoo.auctionseats.domain.payment.controller;

import java.util.HashMap;
import java.util.Map;

import me.kimgunwoo.auctionseats.domain.payment.dto.request.PaymentFromUserRequest;
import me.kimgunwoo.auctionseats.domain.payment.dto.request.PaymentToApiRequest;
import me.kimgunwoo.auctionseats.domain.payment.dto.response.PaymentSuccessResponse;
import me.kimgunwoo.auctionseats.domain.payment.service.PaymentService;
import me.kimgunwoo.auctionseats.domain.user.entity.User;
import me.kimgunwoo.auctionseats.global.annotaion.CurrentUser;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/payments")
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping
    public ResponseEntity<?> requestPayment(
            @CurrentUser User user,
            @RequestBody @Valid PaymentFromUserRequest request
    ) {
        Map<String, Object> status = new HashMap<>();
        PaymentToApiRequest toApiRequest = paymentService.requestPayment(user, request);
        status.put("request", toApiRequest);

        return ResponseEntity.ok(status);
    }

    @PostMapping("/success")
    public ResponseEntity<?> successPayment(@RequestBody String jsonBody) throws Exception {
        PaymentSuccessResponse response = paymentService.successPayment(jsonBody);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/getKey")
    public ResponseEntity<?> getKey(@CurrentUser User user) {
        return ResponseEntity.ok(paymentService.getKey());
    }

}