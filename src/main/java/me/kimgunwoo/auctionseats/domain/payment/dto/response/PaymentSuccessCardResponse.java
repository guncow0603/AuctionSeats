package me.kimgunwoo.auctionseats.domain.payment.dto.response;

import lombok.Data;

@Data
public class PaymentSuccessCardResponse {

    String company;
    String number;
    String installmentPlanMonths;
    String inInterestFree;
    String approveNo;
    String useCardPoint;
    String cardType;
    String ownerType;
    String acquireStatus;
    String receiptUrl;
}