package me.kimgunwoo.auctionseats.domain.auction.dto.request;


import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;

public record AuctionCreateRequest (

    @NotBlank(message = "시작가를 입력해주세요.")
    Long startPrice,

    @NotBlank(message = "경매 시작일시를 입력해주세요.")
    @JsonFormat(pattern = "yyyy-MM-dd HH")
    LocalDateTime startDateTime,

    @NotBlank(message = "경매 마감일시를 입력해주세요.")
    @JsonFormat(pattern = "yyyy-MM-dd HH")
    LocalDateTime endDateTime
    ){}

