package me.kimgunwoo.auctionseats.domain.auction.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.kimgunwoo.auctionseats.domain.user.entity.User;
import me.kimgunwoo.auctionseats.global.entity.BaseEntity;
import org.hibernate.annotations.Comment;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Table(name="auction")
@Entity
public class Bid extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Comment("입찰한 유저")
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Comment("경매")
    @ManyToOne
    @JoinColumn(name = "auction_id")
    private Auction auction;

    private Bid(User user, Auction auction) {
        this.user = user;
        this.auction = auction;
    }

    public static Bid of(User user, Auction auction) {
        return new Bid(user, auction);
    }
}
