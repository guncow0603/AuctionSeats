package me.kimgunwoo.auctionseats.domain.schedule.service;

import me.kimgunwoo.auctionseats.domain.place.entity.Place;
import me.kimgunwoo.auctionseats.domain.schedule.dto.response.ScheduleGetResponse;
import me.kimgunwoo.auctionseats.domain.schedule.entity.Schedule;
import me.kimgunwoo.auctionseats.domain.schedule.repository.ScheduleRepository;
import me.kimgunwoo.auctionseats.domain.show.entity.Shows;
import me.kimgunwoo.auctionseats.domain.show.entity.ShowsInfo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class ScheduleServiceTest {

    @InjectMocks
    ScheduleServiceImpl scheduleService;

    @Mock
    ScheduleRepository scheduleRepository;

    @Test
    void 회차_전체_조회_테스트() {
        // given
        ShowsInfo showsInfo = Mockito.mock();
        Place place = Mockito.mock();
        Shows shows =
                Shows
                        .builder()
                        .showsInfo(showsInfo)
                        .place(place)
                        .startDate(LocalDate.of(2024, 2, 1))
                        .endDate(LocalDate.of(2024, 2, 2))
                        .build();

        ReflectionTestUtils.setField(shows, "id", 1L);

        List<Schedule> scheduleList = new ArrayList<>();
        scheduleList.add(
                Schedule
                        .builder()
                        .sequence(1)
                        .startDateTime(
                                LocalDateTime.of(2023, 3, 1, 15, 0, 0))
                        .shows(shows)
                        .build()
        );
        scheduleList.add(
                Schedule
                        .builder()
                        .sequence(2)
                        .startDateTime(
                                LocalDateTime.of(2023, 3, 2, 15, 0, 0))
                        .shows(shows)
                        .build()
        );
        ReflectionTestUtils.setField(scheduleList.get(0), "id", 1L);
        ReflectionTestUtils.setField(scheduleList.get(1), "id", 2L);

        // when
        given(scheduleRepository.findAllByShowsId(anyLong())).willReturn(scheduleList);
        List<ScheduleGetResponse> scheduleGetResponses = scheduleService.getAllSchedule(1L);

        // then
        verify(scheduleRepository, times(1)).findAllByShowsId(anyLong());
        assertEquals(scheduleGetResponses.get(0).getScheduleId(), scheduleList.get(0).getId());
        assertEquals(scheduleGetResponses.get(1).getScheduleId(), scheduleList.get(1).getId());
        assertEquals(scheduleGetResponses.get(0).getSequence(), scheduleList.get(0).getSequence());
        assertEquals(scheduleGetResponses.get(1).getSequence(), scheduleList.get(1).getSequence());
        assertEquals(scheduleGetResponses.get(0).getStartDateTime(), scheduleList.get(0).getStartDateTime());
        assertEquals(scheduleGetResponses.get(1).getStartDateTime(), scheduleList.get(1).getStartDateTime());
    }

}
