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
    // 공연 회차 별 좌석 저장
    @Override
    public void saveAllShowsSequenceSeat(List<ShowsSequenceSeat> showsSequenceSeatList) {
        showsSequenceSeatRepository.saveAll(showsSequenceSeatList);
    }
    // 회차별 공연 조회
    @Override
    public List<ShowsSequenceSeat> findAllBySequenceId(Long sequenceId) {
        return showsSequenceSeatRepository.findAllBySequenceId(sequenceId);
    }
}
