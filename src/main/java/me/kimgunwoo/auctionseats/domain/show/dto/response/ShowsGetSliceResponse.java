package me.kimgunwoo.auctionseats.domain.show.dto.response;

import lombok.Getter;
import me.kimgunwoo.auctionseats.domain.show.entity.Shows;
import org.springframework.data.domain.Slice;

@Getter
public class ShowsGetSliceResponse {
    private final Slice<ShowsResponse> showsSlice;

    public ShowsGetSliceResponse(Slice<Shows> showsSlice) {
        this.showsSlice = showsSlice.map(ShowsResponse::new);
    }
}