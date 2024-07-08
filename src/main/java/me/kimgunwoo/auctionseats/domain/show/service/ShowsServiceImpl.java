package me.kimgunwoo.auctionseats.domain.show.service;

import lombok.RequiredArgsConstructor;
import me.kimgunwoo.auctionseats.domain.show.entity.Shows;
import me.kimgunwoo.auctionseats.domain.show.entity.ShowsCategory;
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

    // 이미지 저장
    @Override
    public Shows saveShows(Shows shows) {
        return showsRepository.save(shows);
    }

    // 모든 이미지 저장
    @Override
    public List<ShowsImage> saveAllShowsImage(List<ShowsImage> fileUrl) {
        return showsImageRepository.saveAll(fileUrl);
    }

    // 이미지 저장
    @Override
    public ShowsImage saveShowsImage(ShowsImage fileUrl) {
        return showsImageRepository.save(fileUrl);
    }

    // 공연 카테고리 이름 탐색
    @Override
    public ShowsCategory findShowsCategory(String category) {
        return showsCategoryRepository.findByName(category).orElse(null);
    }

    // 공연 카테고리 저장
    @Override
    public ShowsCategory saveShowSCategory(ShowsCategory category) {
        return showsCategoryRepository.save(category);
    }
}