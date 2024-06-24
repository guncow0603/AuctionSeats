package me.kimgunwoo.auctionseats.domain.sequence.repository;

import me.kimgunwoo.auctionseats.domain.sequence.entity.Sequence;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface SequenceRepository extends JpaRepository<Sequence, Long> {
    @Query("select s from Sequence s "
            + "join fetch s.shows g "
            + "join fetch g.showsImage i "
            + "where s.id = :sequenceId and i.type = 'POSTER_IMG'")

    Optional<Sequence> findSequenceWithShowsById(Long sequenceId);
}