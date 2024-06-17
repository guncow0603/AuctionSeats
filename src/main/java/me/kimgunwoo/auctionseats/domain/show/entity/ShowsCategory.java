package me.kimgunwoo.auctionseats.domain.show.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.kimgunwoo.auctionseats.domain.show.dto.request.ShowsRequest;
import me.kimgunwoo.auctionseats.global.entity.BaseEntity;
import org.hibernate.annotations.Comment;


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "shows_category")
public class ShowsCategory extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Comment("카테고리 종류")
    @Column(name = "name", length = 10)
    private String name;

    public static ShowsCategory of(ShowsRequest showsRequest) {
        return new ShowsCategory(showsRequest.categoryName());
    }

    private ShowsCategory(String name) {
        this.name = name;
    }
}
