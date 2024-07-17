package me.kimgunwoo.auctionseats.domain.show.service;

import me.kimgunwoo.auctionseats.domain.admin.dto.request.ShowsCreateRequest;
import me.kimgunwoo.auctionseats.domain.place.entity.Places;
import me.kimgunwoo.auctionseats.domain.show.entity.Shows;
import me.kimgunwoo.auctionseats.domain.show.entity.ShowsInfo;

public interface ShowsService {
    // 공연 생성
    Shows createShows(ShowsCreateRequest goodsCreateRequest, Places places, ShowsInfo goodsInfo);
    
    Shows findById(Long showsId);
}
