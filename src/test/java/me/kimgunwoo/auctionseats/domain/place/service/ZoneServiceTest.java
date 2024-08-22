package me.kimgunwoo.auctionseats.domain.place.service;

import me.kimgunwoo.auctionseats.domain.place.dto.response.ZoneGetResponse;
import me.kimgunwoo.auctionseats.domain.place.entity.Place;
import me.kimgunwoo.auctionseats.domain.place.entity.Zone;
import me.kimgunwoo.auctionseats.domain.place.repository.ZoneRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class ZoneServiceTest {

    @InjectMocks
    ZoneServiceImpl zoneService;

    @Mock
    ZoneRepository zoneRepository;

    @Test
    void 공연_구역_전체조회_테스트() {
        // given
        List<Place> placeList = new ArrayList<>(List.of(Place
                .builder()
                .name("공연장1")
                .address("주소1")
                .countSeats(300)
                .build()
        )
        );
        ReflectionTestUtils.setField(placeList.get(0), "id", 1L);

        List<Zone> zoneList = new ArrayList<>(
                List.of(
                        Zone
                                .builder()
                                .name("A")
                                .seatNumber(100)
                                .build()
                )
        );
        ReflectionTestUtils.setField(zoneList.get(0), "id", 1L);

        // when
        given(zoneRepository.findAllByPlaceId(anyLong())).willReturn(zoneList);
        List<ZoneGetResponse> zoneGetResponse = zoneService.getAllZone(1L);

        // then
        assertEquals(zoneGetResponse.get(0).zoneId(), zoneList.get(0).getId());
        assertEquals(zoneGetResponse.get(0).name(), zoneList.get(0).getName());
        assertEquals(zoneGetResponse.get(0).seatNumber(), zoneList.get(0).getSeatNumber());
    }

}
