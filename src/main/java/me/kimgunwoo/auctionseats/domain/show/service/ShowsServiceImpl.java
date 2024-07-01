package me.kimgunwoo.auctionseats.domain.show.service;

import lombok.RequiredArgsConstructor;
import me.kimgunwoo.auctionseats.domain.show.entity.Shows;
import me.kimgunwoo.auctionseats.domain.show.entity.ShowsImage;
import me.kimgunwoo.auctionseats.domain.show.repository.ShowsCategoryRepository;
import me.kimgunwoo.auctionseats.domain.show.repository.ShowsImageRepository;
import me.kimgunwoo.auctionseats.domain.show.repository.ShowsRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ShowsServiceImpl implements ShowsService {

    public final ShowsRepository showsRepository;

    public final ShowsImageRepository showsImageRepository;

    public final ShowsCategoryRepository showsCategoryRepository;

    public Shows saveShows(Shows shows) {
        return showsRepository.save(shows);
    }

    public List<ShowsImage> saveAllShowsImage(List<ShowsImage> fileUrl) {
        return showsImageRepository.saveAll(fileUrl);
    }

    public ShowsImage saveShowsImage(ShowsImage fileUrl) {
        return showsImageRepository.save(fileUrl);
    }
}
