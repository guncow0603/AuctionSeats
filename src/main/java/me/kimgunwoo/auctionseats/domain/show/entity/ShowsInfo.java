package me.kimgunwoo.auctionseats.domain.show.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.kimgunwoo.auctionseats.global.entity.BaseEntity;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Comment;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "shows_info")
public class ShowsInfo extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Comment("공연 제목")
    @Column(name = "name", length = 100, nullable = false)
    private String name;

    @Comment("공연 내용")
    @Column(name = "description", length = 150, nullable = false)
    private String description;

    @Comment("공연 시간")
    @Column(name = "running_time", nullable = false)
    @ColumnDefault("0")
    private Integer runningTime = 0;

    @Comment("연령대")
    @Column(name = "age_grade", nullable = false)
    @Enumerated(EnumType.STRING)
    private AgeGrade ageGrade;

    @Comment("공연 카테고리")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shows_category_id")
    private ShowsCategory showsCategory;

    @Comment("공연 이미지")
    @OneToMany(mappedBy = "showsInfo", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ShowsImage> showsImage = new ArrayList<>();

    @Comment("공연")
    @OneToMany(mappedBy = "showsInfo", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Shows> shows = new ArrayList<>();

    @Builder
    private ShowsInfo(
            String name,
            String description,
            Integer runningTime,
            Integer ageGrade
    ) {
        this.name = name;
        this.description = description;
        this.runningTime = runningTime;
        this.ageGrade = AgeGrade.of(ageGrade);
    }

    public void addShowsImage(List<ShowsImage> showsImages) {
        this.showsImage.addAll(showsImages);
    }

    public void updateShowsCategory(ShowsCategory showsCategory) {
        this.showsCategory = showsCategory;
    }
    
    public void addShows(Shows shows) {
        this.shows.add(shows);
    }
}
