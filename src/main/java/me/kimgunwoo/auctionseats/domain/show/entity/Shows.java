package me.kimgunwoo.auctionseats.domain.show.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.kimgunwoo.auctionseats.domain.place.entity.Places;
import me.kimgunwoo.auctionseats.domain.admin.dto.request.ShowsRequest;
import me.kimgunwoo.auctionseats.global.entity.BaseEntity;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Comment;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "shows")
public class Shows extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Comment("공연 제목")
    @Column(name = "name", length = 30, nullable = false)
    private String name;

    @Comment("공연 내용")
    @Column(name = "description", length = 150, nullable = false)
    private String description;

    @Comment("공연 시작일")
    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Comment("공연 마감일")
    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;

    @Comment("연령대")
    @Column(name = "age_grade", nullable = false)
    private AgeGrade ageGrade;

    @Comment("공연 시간")
    @Column(name = "running_time", nullable = false)
    @ColumnDefault("0")
    private Integer runningTime = 0;

    @Comment("공연 카테고리")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shows_category_id")
    private ShowsCategory showsCategory;


    @Comment("공연장")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "places_id")
    private Places places;

    @Comment("공연 이미지")
    @OneToMany(mappedBy = "shows", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ShowsImage> showsImage = new ArrayList<>();


    @Builder
    private Shows(
            String name,
            String description,
            LocalDate startDate,
            LocalDate endDate,
            Integer ageGrade,
            Integer runningTime,
            ShowsCategory showsCategory,
            List<ShowsImage> showsImage,
            Places places
    ) {
        this.name = name;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.ageGrade = AgeGrade.of(ageGrade);
        this.runningTime = runningTime;
        this.showsCategory = showsCategory;
        this.showsImage = showsImage;
        this.places = places;
    }

}
