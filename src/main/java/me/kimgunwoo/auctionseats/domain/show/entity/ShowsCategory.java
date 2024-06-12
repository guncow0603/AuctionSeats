package me.kimgunwoo.auctionseats.domain.show.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.kimgunwoo.auctionseats.domain.show.dto.request.ShowsRequest;
import org.hibernate.annotations.Comment;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ShowsCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Comment("카테고리 종류")
    @Column(name = "name")
    private String name;

    public ShowsCategory of(ShowsRequest showsRequest) {
        return new ShowsCategory(showsRequest.categoryName());
    }

    private ShowsCategory(String name) {
        this.name = name;
    }
}
