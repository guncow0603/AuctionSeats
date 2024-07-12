package me.kimgunwoo.auctionseats.domain.show.service;

import lombok.RequiredArgsConstructor;
import me.kimgunwoo.auctionseats.domain.admin.dto.request.ShowsRequest;
import me.kimgunwoo.auctionseats.domain.place.entity.Places;
import me.kimgunwoo.auctionseats.domain.show.entity.Shows;
import me.kimgunwoo.auctionseats.domain.show.entity.ShowsInfo;
import me.kimgunwoo.auctionseats.domain.show.repository.ShowsRepository;
import me.kimgunwoo.auctionseats.global.exception.ApiException;
import org.springframework.stereotype.Service;

import static me.kimgunwoo.auctionseats.global.exception.ErrorCode.NOT_FOUND_SHOWS;

@Service
@RequiredArgsConstructor
public class ShowsServiceImpl implements ShowsService {

    public final ShowsRepository showsRepository;

    public Shows createShows(ShowsRequest showsRequest, Places places, ShowsInfo showsInfo) {
        Shows shows = showsRequest.toShowsEntity(places, showsInfo);

        return showsRepository.save(shows);
    }

    public Shows findById(Long showsId) {
        return showsRepository.findById(showsId)
                .orElseThrow(() -> new ApiException(NOT_FOUND_SHOWS));
    }
}