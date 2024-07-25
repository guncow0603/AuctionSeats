package me.kimgunwoo.auctionseats.domain.show.dto.response;


import lombok.Getter;
import me.kimgunwoo.auctionseats.domain.show.entity.ShowsCategory;

@Getter
public class ShowsCategoryGetResponse {
    private final String categoryName;

    public ShowsCategoryGetResponse(ShowsCategory showsCategory) {
        this.categoryName = showsCategory.getName();
    }
}