package me.kimgunwoo.auctionseats.domain.admin.dto.response;

import lombok.Getter;
import me.kimgunwoo.auctionseats.domain.show.entity.Shows;

import java.time.LocalDate;

@Getter
public class ShowsGetResponse {
    private final Long showsId;

    private final LocalDate startDate;

    private final LocalDate endDate;

    private final String placeName;

    private final String placeAddress;

    public ShowsGetResponse(Shows shows) {
        this.showsId = shows.getId();
        this.startDate = shows.getStartDate();
        this.endDate = shows.getEndDate();
        this.placeName = shows.getPlaces().getName();
        this.placeAddress = shows.getPlaces().getAddress();
    }
}