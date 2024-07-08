package me.kimgunwoo.auctionseats.domain.show.service;

import me.kimgunwoo.auctionseats.domain.show.entity.Shows;
import me.kimgunwoo.auctionseats.domain.show.entity.ShowsCategory;
import me.kimgunwoo.auctionseats.domain.show.entity.ShowsImage;

import java.util.List;

public interface ShowsService {
    // 공연 저장
    Shows saveShows(Shows shows);

    // 모든 이미지 저장
    List<ShowsImage> saveAllShowsImage(List<ShowsImage> fileUrl);

    // 이미지 저장
    ShowsImage saveShowsImage(ShowsImage fileUrl);

    // 공연 카테고리 이름 탐색
    ShowsCategory findShowsCategory(String category);

    // 공연 카테고리 저장
    ShowsCategory saveShowSCategory(ShowsCategory category);
}
