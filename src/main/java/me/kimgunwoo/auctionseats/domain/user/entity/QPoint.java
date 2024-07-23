package me.kimgunwoo.auctionseats.domain.user.entity;

import com.querydsl.core.types.Path;
import com.querydsl.core.types.PathMetadata;
import com.querydsl.core.types.dsl.*;

import javax.annotation.processing.Generated;
import java.time.LocalDateTime;

import static com.querydsl.core.types.PathMetadataFactory.forVariable;

/**
 * QPoint is a Querydsl query type for Point
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPoint extends EntityPathBase<Point> {

    private static final long serialVersionUID = -488815621L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPoint point = new QPoint("point");

    public final me.kimgunwoo.auctionseats.global.entity.QBaseEntity _super = new me.kimgunwoo.auctionseats.global.entity.QBaseEntity(this);

    public final NumberPath<Long> changePoint = createNumber("changePoint", Long.class);

    //inherited
    public final DateTimePath<LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt = _super.modifiedAt;

    public final StringPath orderId = createString("orderId");

    public final EnumPath<me.kimgunwoo.auctionseats.domain.user.enums.PointType> type = createEnum("type", me.kimgunwoo.auctionseats.domain.user.enums.PointType.class);

    public final QUser user;

    public QPoint(String variable) {
        this(Point.class, forVariable(variable), INITS);
    }

    public QPoint(Path<? extends Point> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPoint(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPoint(PathMetadata metadata, PathInits inits) {
        this(Point.class, metadata, inits);
    }

    public QPoint(Class<? extends Point> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.user = inits.isInitialized("user") ? new QUser(forProperty("user")) : null;
    }

}