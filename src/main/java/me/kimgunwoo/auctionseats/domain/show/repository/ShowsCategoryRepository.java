package me.kimgunwoo.auctionseats.domain.show.repository;

import me.kimgunwoo.auctionseats.domain.show.entity.ShowsCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ShowsCategoryRepository extends JpaRepository<ShowsCategory, Long> {
    Optional<ShowsCategory> findByName(String category);

}
