package me.kimgunwoo.auctionseats.domain.seat.repository;

import me.kimgunwoo.auctionseats.domain.seat.entity.Seat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface SeatRepository extends JpaRepository<Seat, Long> {

    @Query("select s from Seat s join fetch s.places where s.id = :seatId")
    Optional<Seat> findSeatWithPlaceById(Long seatId);


    List<Seat> findAllByPlacesIdAndZone(Long placeId, String zone);

    Optional<Seat> findByPlacesIdAndZoneAndSeatNumber(Long placeId, String zone, Integer seatNumber);

    List<Seat> findAllByPlacesIdAndZoneAndSeatNumberIn(Long placeId, String zone, List<Integer> seatNumber);
}
