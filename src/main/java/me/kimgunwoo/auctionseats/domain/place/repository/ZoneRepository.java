package me.kimgunwoo.auctionseats.domain.place.repository;

import me.kimgunwoo.auctionseats.domain.place.entity.Zone;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ZoneRepository extends JpaRepository<Zone, Long> {
}
