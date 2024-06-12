package me.kimgunwoo.auctionseats.domain.show.entity;


import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ShowsImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "key")
    private String key;

    @Column(name = "type")
    private String type;

    @ManyToOne
    @JoinColumn(name = "shows_id")
    private Shows shows;

    public ShowsImage of(String key, String type) {
        return new ShowsImage(key, type);
    }

    private ShowsImage(String key, String type) {
        this.key = key;
        this.type = type;
    }
}