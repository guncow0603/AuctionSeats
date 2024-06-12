package me.kimgunwoo.auctionseats.domain.sequence.repository;

import me.kimgunwoo.auctionseats.domain.sequence.entity.Sequence;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SequenceRepository extends JpaRepository<Sequence,Long> {
}
