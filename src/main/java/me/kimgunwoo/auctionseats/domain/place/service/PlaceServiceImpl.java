package me.kimgunwoo.auctionseats.domain.place.service;

import lombok.RequiredArgsConstructor;
import me.kimgunwoo.auctionseats.domain.admin.dto.ZoneInfo;
import me.kimgunwoo.auctionseats.domain.admin.dto.request.PlacesRequest;
import me.kimgunwoo.auctionseats.domain.place.entity.Places;
import me.kimgunwoo.auctionseats.domain.place.entity.Zone;
import me.kimgunwoo.auctionseats.domain.place.repository.PlaceRepository;
import me.kimgunwoo.auctionseats.domain.place.repository.ZoneRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class PlaceServiceImpl implements PlaceService {

    private final PlaceRepository placesRepository;

    // 공연장 생성
    @Override
    public Places createPlaces(PlacesRequest placesRequest) {
        List<ZoneInfo> zoneInfos = placesRequest.zoneInfos();

        Integer totalSeat = calculateSeats(zoneInfos);

        Places places = placesRequest.toEntity(totalSeat);

        return placesRepository.save(places);
    }

    // 공연장 총 좌석 개수 계산
    @Override
    public Integer calculateSeats(List<ZoneInfo> seats) {
        Integer totalSeat = 0;

        for (ZoneInfo seat : seats) {
            totalSeat += seat.seatNumber();
        }

        return totalSeat;
    }
}