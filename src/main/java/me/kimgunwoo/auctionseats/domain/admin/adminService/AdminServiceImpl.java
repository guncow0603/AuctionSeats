package me.kimgunwoo.auctionseats.domain.admin.adminService;

import lombok.RequiredArgsConstructor;
import me.kimgunwoo.auctionseats.domain.admin.dto.ZoneInfo;
import me.kimgunwoo.auctionseats.domain.admin.dto.request.PlacesRequest;
import me.kimgunwoo.auctionseats.domain.admin.dto.response.PlacesResponse;
import me.kimgunwoo.auctionseats.domain.place.entity.Places;
import me.kimgunwoo.auctionseats.domain.place.entity.Zone;
import me.kimgunwoo.auctionseats.domain.place.service.PlaceService;
import me.kimgunwoo.auctionseats.domain.place.service.ZoneService;
import me.kimgunwoo.auctionseats.domain.show.service.ShowsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final PlaceService placeService;

    private final ZoneService zoneService;

    private final ShowsService showsService;

    public static final String S3_PATH = "https://auction-ticket.s3.ap-northeast-2.amazonaws.com/";

    public static final String FILE_PATH = "shows/";
    public static final String THUMBNAIL = "thumbnail/";

    public static final String GENERAL = "general/";

    // 공연장 및 구역 생성
    @Override
    @Transactional
    public List<PlacesResponse> createPlaceAndZone(PlacesRequest placesRequest) {
        List<ZoneInfo> zoneInfos = placesRequest.zoneInfos();
        Places places = placeService.createPlaces(placesRequest);
        List<Zone> zone = zoneService.createZone(places, zoneInfos);

        return createPlaceResponse(zone);

    }

    // 공연장 및 구역 응답 생성
    @Override
    public List<PlacesResponse> createPlaceResponse(List<Zone> zoneList) {
        List<PlacesResponse> placesResponseList = new ArrayList<>();

        for (Zone zone : zoneList) {
            placesResponseList.add(new PlacesResponse(zone.getName(), zone.getSeatNumber(), zone.getPlaces().getId()));
        }

        return placesResponseList;
    }

}