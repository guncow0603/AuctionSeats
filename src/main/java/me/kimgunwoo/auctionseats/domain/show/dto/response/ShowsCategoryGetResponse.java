package me.kimgunwoo.auctionseats.domain.show.dto.response;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class ShowsCategoryGetResponse {
    private final String categoryName;

    @JsonCreator
    public ShowsCategoryGetResponse(@JsonProperty("showsCategory") String showsCategory) {
        this.categoryName = showsCategory;
    }
}