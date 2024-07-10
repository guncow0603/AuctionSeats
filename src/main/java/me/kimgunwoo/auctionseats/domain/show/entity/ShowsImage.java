package me.kimgunwoo.auctionseats.domain.show.entity;


import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.kimgunwoo.auctionseats.global.entity.BaseEntity;
import org.hibernate.annotations.Comment;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "shows_image")
public class ShowsImage extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Comment("S3 URL")
    @Column(name = "s3_key", length = 150, nullable = false)
    private String s3Key;
    @Comment("대표 이미지 or 일반 이미지")
    @Column(name = "type", length = 10, nullable = false)
    @Enumerated(value = EnumType.STRING)
    private ImageType type;

    @Comment("상품 id")
    @ManyToOne
    @JoinColumn(name = "shows_info_id")
    private ShowsInfo showsInfo;

    @Builder
    private ShowsImage(String s3Key, String type, ShowsInfo showsInfo) {
        this.s3Key = s3Key;
        this.type = ImageType.of(type);
        this.showsInfo = showsInfo;
    }
}