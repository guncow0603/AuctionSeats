package me.kimgunwoo.auctionseats.domain.sequence.service;

import lombok.RequiredArgsConstructor;
import me.kimgunwoo.auctionseats.domain.sequence.entity.Sequence;
import me.kimgunwoo.auctionseats.domain.sequence.repository.SequenceRepository;
import me.kimgunwoo.auctionseats.global.exception.ApiException;
import org.springframework.stereotype.Service;

import java.util.List;

import static me.kimgunwoo.auctionseats.global.exception.ErrorCode.NOT_FOUND_SEQUENCE;

@Service
@RequiredArgsConstructor
public class SequenceServiceImpl implements SequenceService {

    private final SequenceRepository sequenceRepository;

    // 회차 리스트 저장
    @Override
    public void saveAllSequence(List<Sequence> sequenceList) {
        sequenceRepository.saveAll(sequenceList);
    }

    // 회차 저장
    @Override
    public Sequence saveSequence(Sequence sequence) {
        return sequenceRepository.save(sequence);
    }

    // 회차 탐색
    @Override
    public Sequence findSequence(Long sequenceId) {
        return sequenceRepository.findById(sequenceId)
                .orElseThrow(() -> new ApiException(NOT_FOUND_SEQUENCE));
    }
}
