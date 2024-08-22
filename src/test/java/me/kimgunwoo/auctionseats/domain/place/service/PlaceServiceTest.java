package me.kimgunwoo.auctionseats.domain.place.service;

import me.kimgunwoo.auctionseats.domain.place.dto.response.PlaceGetResponse;
import me.kimgunwoo.auctionseats.domain.place.entity.Place;
import me.kimgunwoo.auctionseats.domain.place.repository.PlaceRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class PlaceServiceTest {

    @InjectMocks
    PlaceServiceImpl placeService;

    @Mock
    PlaceRepository placeRepository;

    @Test
    void 공연장_조회_테스트() {
        // given
        List<Place> placeList = new ArrayList<>(List.of(Place
                        .builder()
                        .name("공연장1")
                        .address("주소1")
                        .countSeats(100)
                        .build(),
                Place
                        .builder()
                        .name("공연장2")
                        .address("주소2")
                        .countSeats(100)
                        .build()
        )
        );
        ReflectionTestUtils.setField(placeList.get(0), "id", 1L);
        ReflectionTestUtils.setField(placeList.get(1), "id", 2L);

        //when
        given(placeRepository.findAll()).willReturn(placeList);
        List<PlaceGetResponse> placeGetResponses = placeService.getAllPlace();

        //then
        assertEquals(placeGetResponses.get(0).placeId(), placeList.get(0).getId());
        assertEquals(placeGetResponses.get(1).placeId(), placeList.get(1).getId());
        assertEquals(placeGetResponses.get(0).name(), placeList.get(0).getName());
        assertEquals(placeGetResponses.get(1).name(), placeList.get(1).getName());
    }

}
