package me.kimgunwoo.auctionseats.domain.admin.adminService;

import me.kimgunwoo.auctionseats.domain.admin.dto.request.PlacesRequest;
import me.kimgunwoo.auctionseats.domain.admin.dto.response.PlacesResponse;
import me.kimgunwoo.auctionseats.domain.place.entity.Places;
import me.kimgunwoo.auctionseats.domain.place.service.PlaceServiceImpl;
import me.kimgunwoo.auctionseats.domain.seat.dto.request.SeatRequest;
import me.kimgunwoo.auctionseats.domain.seat.entity.Seat;
import me.kimgunwoo.auctionseats.domain.seat.service.SeatServiceImpl;
import me.kimgunwoo.auctionseats.domain.sequence.service.SequenceServiceImpl;
import me.kimgunwoo.auctionseats.domain.show.service.ShowsServiceImpl;
import me.kimgunwoo.auctionseats.domain.shows_sequence_seat.service.ShowsSequenceSeatServiceImpl;
import me.kimgunwoo.auctionseats.global.util.S3Uploader;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;


import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final ShowsServiceImpl showsService;

    private final PlaceServiceImpl placeService;

    private final SequenceServiceImpl sequenceService;

    private final SeatServiceImpl seatService;

    private final ShowsSequenceSeatServiceImpl showsSequenceSeatService;

    private final S3Uploader s3Uploader;

    // 공연장 생성
    @Override
    @Transactional
    public List<PlacesResponse> createPlace(PlacesRequest placesRequest) {
        List<SeatRequest> seats = placesRequest.seats();
        Integer totalSeat = totalCountSeat(seats);

        Places places = placesRequest.toEntity(totalSeat);
        Places savePlaces = placeService.savePlaces(places);

        List<Seat> seatList = createSeat(seats, savePlaces);
        seatService.saveAllSeat(seatList);

        List<PlacesResponse> placeResponseList = new ArrayList<>();
        for (SeatRequest seat : seats) {
            placeResponseList.add(new PlacesResponse(seat.getZone(), seat.getZoneCountSeat(), places));
        }
        return placeResponseList;
    }

    // 총 좌석 개수 연산
    private Integer totalCountSeat(List<SeatRequest> seatRequests) {
        Integer totalSeat = 0;

        for (SeatRequest seat : seatRequests) {
            totalSeat += seat.getZoneCountSeat();
        }

        return totalSeat;
    }

    // 좌석 생성
    private List<Seat> createSeat(List<SeatRequest> seats, Places places) {
        List<Seat> seatList = new ArrayList<>();

        for (SeatRequest seat : seats) {
            for (int i = 1; i <= seat.getZoneCountSeat(); i++) {
                seatList.add(seat.toEntity(places, i));
            }
        }

        return seatList;
    }

}
