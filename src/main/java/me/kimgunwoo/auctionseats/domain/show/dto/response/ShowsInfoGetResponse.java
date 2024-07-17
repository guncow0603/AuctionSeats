package me.kimgunwoo.auctionseats.domain.show.dto.response;

import lombok.Getter;
import me.kimgunwoo.auctionseats.domain.admin.dto.response.ShowsGetResponse;
import me.kimgunwoo.auctionseats.domain.show.entity.Shows;
import me.kimgunwoo.auctionseats.domain.show.entity.ShowsInfo;

import java.util.List;

@Getter
public class ShowsInfoGetResponse {
    private final List<ShowsGetResponse> showsGetResponse;

    private final String name;

    private final String description;

    private final Integer runningTime;

    private final String ageGrade;

    private final String showsCategory;

    private final List<ShowsImageResponse> showsImages;

    public ShowsInfoGetResponse(ShowsInfo showsInfo, List<Shows> showsList) {
        this.showsGetResponse = showsList.stream().map(ShowsGetResponse::new).toList();
        this.name = showsInfo.getName();
        this.description = showsInfo.getDescription();
        this.runningTime = showsInfo.getRunningTime();
        this.ageGrade = showsInfo.getAgeGrade().getKorea();
        this.showsCategory = showsInfo.getShowsCategory().getName();
        this.showsImages = showsInfo.getShowsImage().stream().map(ShowsImageResponse::new).toList();
    }
}
