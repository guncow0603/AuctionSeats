package me.kimgunwoo.auctionseats.domain.place.service;

import lombok.RequiredArgsConstructor;
import me.kimgunwoo.auctionseats.domain.admin.dto.ZoneInfo;
import me.kimgunwoo.auctionseats.domain.place.dto.response.ZoneGetResponse;
import me.kimgunwoo.auctionseats.domain.place.entity.Places;
import me.kimgunwoo.auctionseats.domain.place.entity.Zone;
import me.kimgunwoo.auctionseats.domain.place.repository.ZoneRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ZoneServiceImpl implements ZoneService {

    private final ZoneRepository zoneRepository;

    // 공연장 구역 생성
    @Override
    public List<Zone> createZone( List<ZoneInfo> zoneInfos) {
        List<Zone> zoneList = new ArrayList<>();

        for (ZoneInfo zoneInfo : zoneInfos) {
            Zone zone =
                    Zone
                            .builder()
                            .name(zoneInfo.zone())
                            .seatNumber(zoneInfo.seatNumber())
                            .build();
            zoneList.add(zone);
        }

        return zoneRepository.saveAll(zoneList);
    }

    // 공연장 구역 전체 조회
    @Override
    @Transactional(readOnly = true)
    public List<ZoneGetResponse> getAllZone(Long placeId) {
        List<Zone> zoneList = zoneRepository.findAllByPlacesId(placeId);
        return zoneList.stream().map(ZoneGetResponse::new).toList();
    }

    // 구역 프록시 객체 생성
    @Override
    public Zone getReferenceById(Long zoneId) {
        return zoneRepository.getReferenceById(zoneId);
    }
}
