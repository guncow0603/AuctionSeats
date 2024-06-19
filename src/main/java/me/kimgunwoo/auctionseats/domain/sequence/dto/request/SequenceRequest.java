package me.kimgunwoo.auctionseats.domain.sequence.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import me.kimgunwoo.auctionseats.domain.sequence.entity.Sequence;
import me.kimgunwoo.auctionseats.domain.show.entity.Shows;

import java.time.LocalDateTime;

public record SequenceRequest (
        @NotNull(message = "회차 정보는 필수입니다.")
        Integer sequence,
        @NotBlank(message = "공연일시 정보는 필수입니다.")
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
        LocalDateTime startDateTime){
    public Sequence toEntity(Shows shows) {
        return Sequence
                .builder()
                .sequence(this.sequence)
                .startDateTime(startDateTime)
                .shows(shows)
                .build();
    }
}
