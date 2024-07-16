package me.kimgunwoo.auctionseats.domain.show.dto.response;

import lombok.Getter;
import me.kimgunwoo.auctionseats.domain.show.entity.ShowsImage;

import static me.kimgunwoo.auctionseats.domain.admin.adminService.AdminServiceImpl.S3_PATH;

@Getter
public class ShowsImageResponse {
    private final String imageType;

    private final String s3Url;

    public ShowsImageResponse(ShowsImage goodsImage) {
        this.imageType = goodsImage.getType().getType();
        this.s3Url = S3_PATH + goodsImage.getS3Key();
    }
}