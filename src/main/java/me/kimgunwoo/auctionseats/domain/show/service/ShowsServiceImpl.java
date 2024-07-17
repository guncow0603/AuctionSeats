package me.kimgunwoo.auctionseats.domain.show.service;

import lombok.RequiredArgsConstructor;
import me.kimgunwoo.auctionseats.domain.admin.dto.request.ShowsCreateRequest;
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

    @Override
    public Shows createShows(ShowsCreateRequest showsCreateRequest, Places places, ShowsInfo showsInfo) {
        Shows shows = showsCreateRequest.toEntity(places, showsInfo);

        return showsRepository.save(shows);
    }

    public Shows findById(Long showsId) {
        return showsRepository.findById(showsId)
                .orElseThrow(() -> new ApiException(NOT_FOUND_SHOWS));
    }
}