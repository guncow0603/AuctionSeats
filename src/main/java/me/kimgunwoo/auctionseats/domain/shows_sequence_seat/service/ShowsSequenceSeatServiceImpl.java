package me.kimgunwoo.auctionseats.domain.shows_sequence_seat.service;

import lombok.RequiredArgsConstructor;
import me.kimgunwoo.auctionseats.domain.show.entity.ShowsCategory;
import me.kimgunwoo.auctionseats.domain.show.repository.ShowsCategoryRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ShowsSequenceSeatServiceImpl implements ShowsSequenceSeatService {

    private final ShowsCategoryRepository showsCategoryRepository;

    public ShowsCategory findShowsCategory(String category) {
        return showsCategoryRepository.findByName(category).orElse(null);
    }

    public ShowsCategory saveShowSCategory(ShowsCategory category) {
        return showsCategoryRepository.save(category);
    }

}
