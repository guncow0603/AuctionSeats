package me.kimgunwoo.auctionseats.domain.shows_sequence_seat.service;

import lombok.RequiredArgsConstructor;
import me.kimgunwoo.auctionseats.domain.shows_sequence_seat.entity.ShowsSequenceSeat;
import me.kimgunwoo.auctionseats.domain.shows_sequence_seat.repository.ShowsSequenceSeatRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ShowsSequenceSeatServiceImpl implements ShowsSequenceSeatService {
    private final ShowsSequenceSeatRepository showsSequenceSeatRepository;

    public void saveAllShowsSequenceSeat(List<ShowsSequenceSeat> showsSequenceSeatList) {
        showsSequenceSeatRepository.saveAll(showsSequenceSeatList);
    }

    public List<ShowsSequenceSeat> findAllBySequenceId(Long sequenceId) {
        return showsSequenceSeatRepository.findAllBySequenceId(sequenceId);
    }
}
