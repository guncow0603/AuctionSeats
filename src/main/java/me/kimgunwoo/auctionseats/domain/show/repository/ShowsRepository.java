package me.kimgunwoo.auctionseats.domain.show.repository;

import me.kimgunwoo.auctionseats.domain.show.entity.Shows;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShowsRepository extends JpaRepository<Shows, Long>, ShowsRepositoryCustom {

}
