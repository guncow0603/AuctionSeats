package me.kimgunwoo.auctionseats.domain.place.service;

import lombok.RequiredArgsConstructor;
import me.kimgunwoo.auctionseats.domain.admin.dto.ZoneInfo;
import me.kimgunwoo.auctionseats.domain.place.entity.Places;
import me.kimgunwoo.auctionseats.domain.place.entity.Zone;
import me.kimgunwoo.auctionseats.domain.place.repository.ZoneRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ZoneServiceImpl implements ZoneService {

    private final ZoneRepository zoneRepository;

    // 공연장 구역 생성
    @Override
    public List<Zone> createZone(Places places, List<ZoneInfo> zoneInfos) {
        List<Zone> zoneList = new ArrayList<>();

        for (ZoneInfo zoneInfo : zoneInfos) {
            Zone zone =
                    Zone
                            .builder()
                            .places(places)
                            .name(zoneInfo.zone())
                            .seatNumber(zoneInfo.seatNumber())
                            .build();
            zoneList.add(zone);
        }

        return zoneRepository.saveAll(zoneList);
    }

    // 구역 프록시 객체 생성
    @Override
    public Zone getReferenceById(Long zoneId) {
        return zoneRepository.getReferenceById(zoneId);
    }
}
