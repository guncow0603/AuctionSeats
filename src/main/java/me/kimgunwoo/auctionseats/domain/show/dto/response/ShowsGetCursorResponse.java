package me.kimgunwoo.auctionseats.domain.show.dto.response;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.List;

@Getter
public class ShowsGetCursorResponse {
    private final List<ShowsGetQueryResponse> showsResponses;
    private final Long nextCursorId;

    @JsonCreator
    public ShowsGetCursorResponse(
            @JsonProperty("showsGetQueryResponses") List<ShowsGetQueryResponse> showsGetQueryResponses,
            @JsonProperty("nextCursorId") Long nextCursorId
    ) {
        this.showsResponses = showsGetQueryResponses;
        this.nextCursorId = nextCursorId;
    }

}
