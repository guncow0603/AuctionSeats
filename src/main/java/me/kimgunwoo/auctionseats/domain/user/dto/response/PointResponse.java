package me.kimgunwoo.auctionseats.domain.user.dto.response;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import me.kimgunwoo.auctionseats.domain.user.enums.PointType;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
public class PointResponse {
    private Long id;
    private String time;
    private Long amount;
    private String type;

    @QueryProjection
    public PointResponse(Long id, LocalDateTime createdAt, Long amount, PointType type) {
        this.id = id;
        this.time = createdAt.format(DateTimeFormatter.ofPattern("yyyy년 MM월 dd일"));
        this.amount = amount;
        this.type = type.getType();
    }
}