package me.kimgunwoo.auctionseats.domain.show.dto.response;

import lombok.Getter;
import me.kimgunwoo.auctionseats.domain.show.entity.ShowsInfo;

import static me.kimgunwoo.auctionseats.domain.admin.adminService.AdminServiceImpl.S3_PATH;

@Getter
public class ShowsInfoResponse {
    private final Long showsInfoId;

    private final String name;

    private final String s3Url;

    public ShowsInfoResponse(ShowsInfo showsInfo) {
        this.showsInfoId = showsInfo.getId();
        this.name = showsInfo.getName();
        this.s3Url = S3_PATH + showsInfo.getShowsImage().get(0).getS3Key();
    }
}