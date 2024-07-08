package me.kimgunwoo.auctionseats.domain.place.service;

import lombok.RequiredArgsConstructor;
import me.kimgunwoo.auctionseats.domain.place.entity.Places;
import me.kimgunwoo.auctionseats.domain.place.repository.PlaceRepository;
import me.kimgunwoo.auctionseats.global.exception.ApiException;
import org.springframework.stereotype.Service;

import static me.kimgunwoo.auctionseats.global.exception.ErrorCode.NOT_FOUND_PLACE;


@Service
@RequiredArgsConstructor
public class PlaceServiceImpl implements PlaceService {

    private final PlaceRepository placeRepository;

    // 공연장 저장
    @Override
    public Places savePlace(Places place) {
        return placeRepository.save(place);
    }

    // 공연장 찾기
    @Override
    public Places findPlace(Long placeId) {
        return placeRepository.findById(placeId)
                .orElseThrow(() -> new ApiException(NOT_FOUND_PLACE));
    }
}