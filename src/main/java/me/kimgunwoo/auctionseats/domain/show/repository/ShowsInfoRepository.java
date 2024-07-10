package me.kimgunwoo.auctionseats.domain.show.repository;

import me.kimgunwoo.auctionseats.domain.show.entity.ShowsInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShowsInfoRepository extends JpaRepository<ShowsInfo, Long> {
}