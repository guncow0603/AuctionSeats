package me.kimgunwoo.auctionseats.domain.show.dto.response;

import lombok.Getter;
import me.kimgunwoo.auctionseats.domain.show.entity.ShowsInfo;
import org.springframework.data.domain.Slice;

@Getter
public class ShowsInfoGetSliceResponse {
    private final Slice<ShowsInfoResponse> showsInfoSlice;

    public ShowsInfoGetSliceResponse(Slice<ShowsInfo> showsInfoSlice) {
        this.showsInfoSlice = showsInfoSlice.map(ShowsInfoResponse::new);
    }
}