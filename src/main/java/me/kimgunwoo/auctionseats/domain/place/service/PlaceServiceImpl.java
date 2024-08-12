package me.kimgunwoo.auctionseats.domain.place.service;

import lombok.RequiredArgsConstructor;
import me.kimgunwoo.auctionseats.domain.place.dto.ZoneInfo;
import me.kimgunwoo.auctionseats.domain.admin.dto.request.PlaceCreateRequest;
import me.kimgunwoo.auctionseats.domain.place.dto.response.PlaceGetResponse;
import me.kimgunwoo.auctionseats.domain.place.entity.Place;
import me.kimgunwoo.auctionseats.domain.place.repository.PlaceRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PlaceServiceImpl implements PlaceService {
    private final PlaceRepository placeRepository;
    // 공연장 생성
    @Override
    public Place createPlace(PlaceCreateRequest placeCreateRequest) {
        List<ZoneInfo> zoneInfos = placeCreateRequest.zoneInfos();

        Integer totalSeat = calculateSeats(zoneInfos);

        Place place = placeCreateRequest.toEntity(totalSeat);

        return placeRepository.save(place);
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

    // 공연장 전체조회
    @Override
    @Transactional(readOnly = true)
    public List<PlaceGetResponse> getAllPlace() {
        List<Place> placeList = placeRepository.findAll();
        return placeList.stream().map(PlaceGetResponse::new).toList();
    }

    // 공연장 프록시 객체 조회
    @Override
    public Place getReferenceById(Long placeId) {
        return placeRepository.getReferenceById(placeId);
    }

}