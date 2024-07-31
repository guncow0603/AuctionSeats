package me.kimgunwoo.auctionseats.domain.place.repository;

import me.kimgunwoo.auctionseats.domain.place.entity.Place;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlaceRepository extends JpaRepository<Place,Long> {
}
