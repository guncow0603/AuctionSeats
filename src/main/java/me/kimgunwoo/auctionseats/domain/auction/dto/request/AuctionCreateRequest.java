package me.kimgunwoo.auctionseats.domain.auction.dto.request;


import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;

public record AuctionCreateRequest (

    @NotBlank(message = "시작가를 입력해주세요.")
    Long startPrice,

    @JsonFormat(pattern = "yyyy-MM-dd HH")
    LocalDate startDate,

    @JsonFormat(pattern = "yyyy-MM-dd HH")
    LocalDate endDate
    ){}

