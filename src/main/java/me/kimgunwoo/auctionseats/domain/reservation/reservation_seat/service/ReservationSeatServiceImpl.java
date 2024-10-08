package me.kimgunwoo.auctionseats.domain.reservation.reservation_seat.service;

import lombok.RequiredArgsConstructor;
import me.kimgunwoo.auctionseats.domain.reservation.reservation_seat.entity.ReservationSeat;
import me.kimgunwoo.auctionseats.domain.reservation.reservation_seat.repository.ReservationSeatRepository;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.TimeUnit;

import static me.kimgunwoo.auctionseats.domain.reservation.reservation_seat.repository.ReservationSeatQueryRepositoryImpl.SEAT_CACHE_PREFIX;

@Service
@RequiredArgsConstructor
public class ReservationSeatServiceImpl implements ReservationSeatService {

    private final RedisTemplate<String, Object> redisTemplate;
    private final ReservationSeatRepository seatRepository;

    @Override
    @EventListener(ApplicationReadyEvent.class) // 서버 모든 준비가 끝나면 캐시 업로드
    public void seatCacheWarmUp() {

        // 워밍업 작업은 한 인스턴스만 실행하도록 한다.
        String lockKey = "SEAT_WARM_UP_LOCK";
        String lockValue = "locked";
        Boolean acquired = redisTemplate.opsForValue().setIfAbsent(lockKey, lockValue, 20, TimeUnit.MINUTES);
        if (Boolean.FALSE.equals(acquired)) {
            return;
        }

        int pageNumber = 0;
        int pageSize = 1000; // 페이지 당 요소 수
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        LocalDateTime now = LocalDateTime.now();
        Slice<ReservationSeat> slice;
        do {
            slice = seatRepository.findByScheduleStartDateTimeGreaterThan(now, pageable);
            if (slice.isEmpty()) {
                break;
            }

            List<ReservationSeat> seats = slice.getContent();
            Map<String, List<Integer>> cache = new HashMap<>(); //
            Map<String, Set<Long>> scheduleZoneGrades = new HashMap<>();
            Map<String, LocalDateTime> timeMap = new HashMap<>();

            // 모든 좌석 순회하면서 각 좌석을 Schedule:ZoneGradeId 형식의 key에, value는 seatNumbers
            seats.forEach((seat) -> {
                Long scheduleId = seat.getId().getScheduleId();
                Long zoneGradeId = seat.getId().getZoneGradeId();
                Integer seatNumber = seat.getId().getSeatNumber();

                // 회차별 시작 시간 저장
                timeMap.put(scheduleId.toString(), seat.getSchedule().getStartDateTime());
                String szKey = "{%s%d}".formatted(SEAT_CACHE_PREFIX, scheduleId);
                // 스케쥴에 포함된 구역등급들을 저장한다
                Set<Long> zoneGrades = scheduleZoneGrades.getOrDefault(szKey, new HashSet<>());
                zoneGrades.add(zoneGradeId);
                scheduleZoneGrades.put(szKey, zoneGrades);

                String cacheKey = "{%s%d}:%d".formatted(SEAT_CACHE_PREFIX, scheduleId, zoneGradeId);
                // 캐시에 좌석번호를 저장한다
                List<Integer> seatNumbers = cache.getOrDefault(cacheKey, new ArrayList<>());
                seatNumbers.add(seatNumber);
                cache.put(cacheKey, seatNumbers);
            });

            // schedule에 포함된 zoneGradeId 목록 redis에 등록
            Set<Map.Entry<String, Set<Long>>> szEntry = scheduleZoneGrades.entrySet();

            szEntry.forEach(entry -> {
                String scheduleId = entry.getKey().substring(
                        entry.getKey().indexOf(":") + 1,
                        entry.getKey().indexOf("}")
                );
                Duration between = Duration.between(now, timeMap.get(scheduleId));
                long ttl = TimeUnit.SECONDS.toSeconds(between.getSeconds()); // TTL 값 설정

                redisTemplate.opsForValue().set(entry.getKey(), entry.getValue(), ttl, TimeUnit.SECONDS);
            });

            // 캐시 redis에 등록
            Set<Map.Entry<String, List<Integer>>> cacheEntry = cache.entrySet();
            cacheEntry.forEach(entry -> {
                String scheduleId = entry.getKey().substring(
                        entry.getKey().indexOf(":") + 1,
                        entry.getKey().indexOf("}")
                );
                Duration between = Duration.between(now, timeMap.get(scheduleId));
                long ttl = TimeUnit.SECONDS.toSeconds(between.getSeconds()); // TTL 값 설정
                redisTemplate.opsForValue().set(entry.getKey(), entry.getValue(), ttl, TimeUnit.SECONDS);
            });

            pageable = slice.nextPageable(); // 다음 페이지를 위한 Pageable 객체 준비
        } while (slice.hasNext()); // 다음 페이지가 있는지 확인
    }
}

