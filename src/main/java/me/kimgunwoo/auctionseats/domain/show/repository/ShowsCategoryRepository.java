package me.kimgunwoo.auctionseats.domain.show.repository;

import me.kimgunwoo.auctionseats.domain.show.entity.ShowsCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShowsCategoryRepository extends JpaRepository<ShowsCategory, Long> {

}
