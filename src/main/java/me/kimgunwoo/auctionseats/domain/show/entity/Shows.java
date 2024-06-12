package me.kimgunwoo.auctionseats.domain.show.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.kimgunwoo.auctionseats.domain.place.entity.Places;
import me.kimgunwoo.auctionseats.domain.show.dto.request.ShowsRequest;
import org.hibernate.annotations.Comment;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Shows {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Comment("공연 제목")
    @Column(name = "name")
    private String name;

    @Comment("공연 내용")
    @Column(name = "description")
    private String description;

    @Comment("공연 시작일")
    @Column(name = "start_date")
    private LocalDateTime startDate;

    @Comment("공연 마감일")
    @Column(name = "end_date")
    private LocalDateTime endDate;

    @Comment("연령대")
    @Column(name = "age_grade")
    private AgeGrade ageGrade;

    @Comment("공연 시간")
    @Column(name = "running_time")
    private String runningTime;

    @Comment("공연 카테고리")
    @ManyToOne
    @JoinColumn(name = "shows_category_id")
    private ShowsCategory showsCategory;


    @Comment("공연장")
    @ManyToOne
    @JoinColumn(name = "places_id")
    private Places places;

    @Comment("공연 이미지")
    @OneToMany(mappedBy = "shows", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ShowsImage> showsImage = new ArrayList<>();

    public static Shows of(
            ShowsRequest showsRequest,
            ShowsCategory showsCategory,
            List<ShowsImage> showsImage,
            Places places
    ) {
        return new Shows(
                showsRequest.name(),
                showsRequest.description(),
                showsRequest.startDate(),
                showsRequest.endDate(),
                showsRequest.ageGrade(),
                showsRequest.runningTime(),
                showsCategory,
                showsImage,
                places
        );
    }

    private Shows(
            String name,
            String description,
            LocalDateTime startDate,
            LocalDateTime endDate,
            int ageGrade,
            String runningTime,
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