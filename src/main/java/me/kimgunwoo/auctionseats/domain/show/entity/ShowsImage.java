package me.kimgunwoo.auctionseats.domain.show.entity;


import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ShowsImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Comment("S3 URL")
    @Column(name = "key")
    private String key;

    @Comment("대표 이미지 or 일반 이미지")
    @Column(name = "type")
    private ImageType type;

    @Comment("공연 id")
    @ManyToOne
    @JoinColumn(name = "shows_id")
    private Shows shows;

    public ShowsImage of(String key, String type) {
        return new ShowsImage(key, type);
    }

    private ShowsImage(String key, String type) {
        this.key = key;
        this.type = ImageType.of(type);
    }
}