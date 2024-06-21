package me.kimgunwoo.auctionseats.domain.sequence.repository;

import me.kimgunwoo.auctionseats.domain.sequence.entity.Sequence;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SequenceRepository extends JpaRepository<Sequence, Long> {

    Optional<Sequence> findSequenceWithShowsById(Long sequenceId);
}