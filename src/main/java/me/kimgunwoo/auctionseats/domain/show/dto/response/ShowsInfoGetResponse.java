package me.kimgunwoo.auctionseats.domain.show.dto.response;

import lombok.Getter;
import me.kimgunwoo.auctionseats.domain.show.entity.AgeGrade;
import me.kimgunwoo.auctionseats.domain.show.entity.ShowsCategory;
import me.kimgunwoo.auctionseats.domain.show.entity.ShowsImage;
import me.kimgunwoo.auctionseats.domain.show.entity.ShowsInfo;

import java.util.List;

@Getter
public class ShowsInfoGetResponse {
    private final String name;

    private final String description;

    private final Integer runningTime;

    private final AgeGrade ageGrade;

    private final ShowsCategory showsCategory;

    private final List<ShowsImage> showsImages;

    public ShowsInfoGetResponse(ShowsInfo showsInfo) {
        this.name = showsInfo.getName();
        this.description = showsInfo.getDescription();
        this.runningTime = showsInfo.getRunningTime();
        this.ageGrade = showsInfo.getAgeGrade();
        this.showsCategory = showsInfo.getShowsCategory();
        this.showsImages = showsInfo.getShowsImage();
    }
}
