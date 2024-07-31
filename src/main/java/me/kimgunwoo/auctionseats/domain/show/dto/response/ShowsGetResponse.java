package me.kimgunwoo.auctionseats.domain.show.dto.response;

import lombok.Getter;
import me.kimgunwoo.auctionseats.domain.show.entity.Shows;
import me.kimgunwoo.auctionseats.domain.show.entity.ShowsImage;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import static me.kimgunwoo.auctionseats.domain.admin.adminService.AdminServiceImpl.S3_PATH;

@Getter
public class ShowsGetResponse {
    private final Long showsId;

    private final String title;

    private final LocalDate startDate;

    private final LocalDate endDate;

    private final Integer runningTime;

    private final List<String> s3Urls;

    private final String ageGrade;

    private final Long placeId;

    private final String placeName;

    private final String placeAddress;

    public ShowsGetResponse(Shows shows) {
        this.showsId = shows.getId();
        this.title = shows.getTitle();
        this.startDate = shows.getStartDate();
        this.endDate = shows.getEndDate();
        this.runningTime = shows.getShowsInfo().getRunningTime();
        this.s3Urls = shows.getShowsInfo().getShowsImage().stream()
                .map(ShowsImage::getS3Key)
                .map(s3Key -> S3_PATH + s3Key)
                .collect(Collectors.toList());
        this.ageGrade = shows.getShowsInfo().getAgeGrade().getKorea();
        this.placeId = shows.getPlace().getId();
        this.placeName = shows.getPlace().getName();
        this.placeAddress = shows.getPlace().getAddress();
    }
}
