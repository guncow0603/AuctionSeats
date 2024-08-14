package me.kimgunwoo.auctionseats.domain.payment.dto.response;

public record PaymentSuccessCardResponse(
        String company,
        String number,
        String installmentPlanMonths,
        String inInterestFree,
        String approveNo,
        String useCardPoint,
        String cardType,
        String ownerType,
        String acquireStatus,
        String receiptUrl
) {}