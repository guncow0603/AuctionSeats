package me.kimgunwoo.auctionseats.domain.show.entity;


import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
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
    @Column(name = "s3_key", length = 50, nullable = false)
    private String s3key;

    @Comment("대표 이미지 or 일반 이미지")
    @Column(name = "type", length = 10, nullable = false)
    private ImageType type;

    @Comment("공연 id")
    @ManyToOne
    @JoinColumn(name = "shows_id")
    private Shows shows;

    @Builder
    private ShowsImage(String s3key, String type) {
        this.s3key = s3key;
        this.type = ImageType.of(type);
    }
}