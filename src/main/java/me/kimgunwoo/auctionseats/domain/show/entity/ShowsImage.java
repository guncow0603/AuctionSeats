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
    @Column(name = "s3_key", length = 50)
    private String s3key;

    @Comment("대표 이미지 or 일반 이미지")
    @Column(name = "type", length = 10)
    private ImageType type;

    @Comment("공연 id")
    @ManyToOne
    @JoinColumn(name = "shows_id")
    private Shows shows;

    public static ShowsImage of(String s3key, String type) {
        return new ShowsImage(s3key, type);
    }

    private ShowsImage(String s3key, String type) {
        this.s3key = s3key;
        this.type = ImageType.of(type);
    }
}