package me.kimgunwoo.auctionseats.domain.user.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.kimgunwoo.auctionseats.domain.user.enums.PointType;
import me.kimgunwoo.auctionseats.global.entity.BaseEntity;
import org.hibernate.annotations.Comment;

@Entity
@Getter
@Table(name = "point_logs")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Point extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Comment("회원")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Comment("변동된 포인트")
    @Column(name = "change_point", nullable = false)
    private Long changePoint;

    @Comment("변동 타입")
    @Enumerated(EnumType.STRING)
    private PointType type;

    @Column(name = "order_id")
    @Comment("충전 시 결제 아이디")
    private String orderId;

    @Builder
    public Point(User user, Long changePoint, PointType type, String orderId) {
        this.user = user;
        this.changePoint = changePoint;
        this.type = type;
        this.orderId = orderId;
    }
}