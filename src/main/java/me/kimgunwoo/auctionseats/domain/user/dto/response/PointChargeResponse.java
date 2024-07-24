package me.kimgunwoo.auctionseats.domain.user.dto.response;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
public class PointChargeResponse {
    private Long id;
    private String time;
    private Long amount;
    private String orderId;

    @QueryProjection
    public PointChargeResponse(Long id, LocalDateTime createdAt, Long amount, String orderId) {
        this.id = id;
        this.time = createdAt.format(DateTimeFormatter.ofPattern("yyyy년 MM월 dd일"));
        this.amount = amount;
        this.orderId = orderId;
    }
}