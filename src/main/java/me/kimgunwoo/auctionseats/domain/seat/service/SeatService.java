package me.kimgunwoo.auctionseats.domain.seat.service;

import me.kimgunwoo.auctionseats.domain.seat.entity.Seat;

import java.util.List;

public interface SeatService {
    // 모든 좌석 조회
    void saveAllSeat(List<Seat> seats);

    // 좌석 조회
    Seat findSeat(Long placeId, String zone, Integer seatNumber);

    // 구역별 좌석 조회
    List<Seat> findAllSeatOfZone(Long placeId, String zone);

    // 특정 좌석 리스트 조회
    List<Seat> findAllSeatNumber(Long placeId, String zone, List<Integer> seatNumbers);
}
