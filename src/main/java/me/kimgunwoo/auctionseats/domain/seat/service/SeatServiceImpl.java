package me.kimgunwoo.auctionseats.domain.seat.service;

import lombok.RequiredArgsConstructor;
import me.kimgunwoo.auctionseats.domain.seat.entity.Seat;
import me.kimgunwoo.auctionseats.domain.seat.repository.SeatRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SeatServiceImpl implements SeatService {

    private final SeatRepository seatRepository;

    public List<Seat> saveAllSeat(List<Seat> seats) {
        return seatRepository.saveAll(seats);
    }
}
