package me.kimgunwoo.auctionseats.domain.sequence.service;

import me.kimgunwoo.auctionseats.domain.sequence.entity.Sequence;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SequenceService {
    // 회차 리스트 저장
    void saveAllSequence(List<Sequence> sequenceList);

    // 회차 저장
    Sequence saveSequence(Sequence sequence);

    // 회차 탐색
    Sequence findSequence(Long sequenceId);
}
