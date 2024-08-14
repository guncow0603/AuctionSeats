package me.kimgunwoo.auctionseats.domain.show.dto.response;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import static me.kimgunwoo.auctionseats.domain.admin.service.AdminServiceImpl.S3_PATH;

@Getter
public class ShowsGetQueryResponse {
    private final Long showsId;
    private final String title;
    private final String s3Url;

    @JsonCreator
    public ShowsGetQueryResponse(
            @JsonProperty("showsId") Long showsId,
            @JsonProperty("title") String title,
            @JsonProperty("s3Key") String s3Key) {
        this.showsId = showsId;
        this.title = title;
        if (s3Key != null) {
            this.s3Url = S3_PATH + s3Key;
        } else {
            this.s3Url = null;
        }

    }
}
