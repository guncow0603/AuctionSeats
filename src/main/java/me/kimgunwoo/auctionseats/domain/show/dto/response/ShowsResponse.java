package me.kimgunwoo.auctionseats.domain.show.dto.response;

import lombok.Getter;
import me.kimgunwoo.auctionseats.domain.show.entity.ImageType;
import me.kimgunwoo.auctionseats.domain.show.entity.Shows;
import me.kimgunwoo.auctionseats.domain.show.entity.ShowsImage;

import static me.kimgunwoo.auctionseats.domain.admin.service.AdminServiceImpl.S3_PATH;

@Getter
public class ShowsResponse {
    private final Long showsId;

    private final String title;

    private final String s3Url;

    public ShowsResponse(Shows shows) {
        this.showsId = shows.getId();
        this.title = shows.getTitle();
        this.s3Url = S3_PATH + shows.getShowsInfo().getShowsImage().stream()
                .filter(image -> ImageType.POSTER_IMG.equals(image.getType()))
                .map(ShowsImage::getS3Key)
                .findFirst()
                .orElse(null);
    }
}