package me.kimgunwoo.auctionseats.domain.place.repository;

import me.kimgunwoo.auctionseats.domain.place.entity.Zone;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ZoneRepository extends JpaRepository<Zone, Long> {

    // 해당 공연장에 있는 구역정보 전체 조회
    List<Zone> findAllByPlacesId(Long placeId);
}
