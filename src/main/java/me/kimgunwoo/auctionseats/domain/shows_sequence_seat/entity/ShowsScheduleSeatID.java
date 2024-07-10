package me.kimgunwoo.auctionseats.domain.shows_sequence_seat.entity;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShowsScheduleSeatID implements Serializable {
    private Long seatId;

    private Long sequenceId;
}