package me.kimgunwoo.auctionseats.domain.show.dto.response;

import lombok.Getter;
import me.kimgunwoo.auctionseats.domain.show.entity.ImageType;
import me.kimgunwoo.auctionseats.domain.show.entity.ShowsImage;
import me.kimgunwoo.auctionseats.domain.show.entity.ShowsInfo;

import static me.kimgunwoo.auctionseats.domain.admin.service.AdminServiceImpl.S3_PATH;

@Getter
public class ShowsInfoGetResponse {
    private final Long showsInfoId;

    private final String name;

    private final String s3Url;

    public ShowsInfoGetResponse(ShowsInfo showsInfo) {
        this.showsInfoId = showsInfo.getId();
        this.name = showsInfo.getName();
        this.s3Url = S3_PATH + showsInfo.getShowsImage().stream()
                .filter(image -> ImageType.POSTER_IMG.equals(image.getType()))
                .map(ShowsImage::getS3Key)
                .findFirst()
                .orElse(null);
    }
}