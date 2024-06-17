package me.kimgunwoo.auctionseats.domain.show.entity;


import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.kimgunwoo.auctionseats.global.entity.BaseEntity;
import org.hibernate.annotations.Comment;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "shows_image")
public class ShowsImage extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Comment("S3 URL")
    @Column(name = "key", length = 50)
    private String key;

    @Comment("대표 이미지 or 일반 이미지")
    @Column(name = "type", length = 10)
    private ImageType type;

    @Comment("공연 id")
    @ManyToOne
    @JoinColumn(name = "shows_id")
    private Shows shows;

    public static ShowsImage of(String key, String type) {
        return new ShowsImage(key, type);
    }

    private ShowsImage(String key, String type) {
        this.key = key;
        this.type = ImageType.of(type);
    }
}