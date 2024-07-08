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

    public void saveAllSeat(List<Seat> seats) {
        seatRepository.saveAll(seats);
    }

    public Seat findSeat(Long placeId, String zone, Integer seatNumber) {
        return seatRepository.findByPlacesIdAndZoneAndSeatNumber(placeId, zone, seatNumber).orElseThrow();
    }

    public List<Seat> findAllSeatOfZone(Long placeId, String zone) {
        return seatRepository.findAllByPlacesIdAndZone(placeId, zone);
    }

    public List<Seat> findAllSeatNumber(Long placeId, String zone, List<Integer> seatNumbers) {
        return seatRepository.findAllByPlacesIdAndZoneAndSeatNumberIn(placeId, zone, seatNumbers);
    }
}
