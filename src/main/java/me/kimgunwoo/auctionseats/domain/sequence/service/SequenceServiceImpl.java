package me.kimgunwoo.auctionseats.domain.sequence.service;

import lombok.RequiredArgsConstructor;
import me.kimgunwoo.auctionseats.domain.sequence.entity.Sequence;
import me.kimgunwoo.auctionseats.domain.sequence.repository.SequenceRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SequenceServiceImpl implements SequenceService {

    private final SequenceRepository sequenceRepository;

    public void saveAllSequence(List<Sequence> sequenceList) {
        sequenceRepository.saveAll(sequenceList);
    }

    public Sequence saveSequence(Sequence sequence) {
        return sequenceRepository.save(sequence);
    }

}
