package me.kimgunwoo.auctionseats.domain.show.repository;

import me.kimgunwoo.auctionseats.domain.show.entity.ShowsImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShowsImageRepository extends JpaRepository<ShowsImage, Long> {
}
