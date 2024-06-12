package me.kimgunwoo.auctionseats.domain.sequence.dto.request;

import lombok.Getter;

import java.time.LocalDateTime;

public record SequenceRequest (
        int sequence,
        LocalDateTime startDateTime){}
