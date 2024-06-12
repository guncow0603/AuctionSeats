package me.kimgunwoo.auctionseats.domain.show.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.kimgunwoo.auctionseats.domain.place.entity.Places;
import me.kimgunwoo.auctionseats.domain.show.dto.request.ShowsRequest;

import java.time.LocalDateTime;
import java.util.List;
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Shows {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "start_date")
    private LocalDateTime startDate;

    @Column(name = "end_date")
    private LocalDateTime endDate;

    @Column(name = "age_grade")
    private AgeGrade ageGrade;

    @Column(name = "running_time")
    private String runningTime;

    @ManyToOne
    @JoinColumn(name = "shows_category_id")
    private ShowsCategory showsCategory;

    @ManyToOne
    @JoinColumn(name = "places_id")
    private Places places;

    @OneToMany(mappedBy = "shows", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ShowsImage> showsImage;

    public static Shows of(ShowsRequest showsRequest, ShowsCategory showsCategory, List<ShowsImage> showsImage,
                           Places places) {
        return new Shows(
                showsRequest.name(),
                showsRequest.description(),
                showsRequest.startDate(),
                showsRequest.endDate(),
                showsRequest.ageGrade(),
                showsRequest.runningTime(),
                showsCategory,
                showsImage,
                places);
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
