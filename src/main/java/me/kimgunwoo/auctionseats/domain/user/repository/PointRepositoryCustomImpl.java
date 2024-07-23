package me.kimgunwoo.auctionseats.domain.user.repository;

import me.kimgunwoo.auctionseats.domain.user.dto.response.PointChargeResponse;
import me.kimgunwoo.auctionseats.domain.user.enums.PointType;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.support.PageableExecutionUtils;


import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

import java.util.List;

import static me.kimgunwoo.auctionseats.domain.user.entity.QPoint.point;

@Repository
public class PointRepositoryCustomImpl implements PointRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;

    public PointRepositoryCustomImpl(JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
    }

    @Override
    public Page<PointChargeResponse> findChargePointListByPage(Long userId, Pageable pageable) {
        List<PointChargeResponse> list = jpaQueryFactory
                .select(Projections.constructor(PointChargeResponse.class,
                        point.id,
                        point.createdAt,
                        point.changePoint,
                        point.orderId
                ))
                .from(point)
                .where(
                        point.user.id.eq(userId)
                                .and(point.type.eq(PointType.CHARGE))
                )
                .orderBy(sortPoint(pageable))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> count = jpaQueryFactory.select(point.count())
                .from(point);

        return PageableExecutionUtils.getPage(list, pageable, count::fetchOne);
    }


    @Override
    public Page<PointUseResponse> findUsePointListByPage(Long userId, Pageable pageable) {
        List<PointUseResponse> list = jpaQueryFactory
                .select(Projections.constructor(PointUseResponse.class,
                        point.id,
                        point.createdAt,
                        point.changePoint
                ))
                .from(point)
                .where(
                        point.user.id.eq(userId)
                                .and(point.type.eq(PointType.USE))
                )
                .orderBy(sortPoint(pageable))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> count = jpaQueryFactory.select(point.count())
                .from(point);

        return PageableExecutionUtils.getPage(list, pageable, count::fetchOne);
    }

    private OrderSpecifier<?> sortPoint(Pageable pageable) {
        OrderSpecifier<?> orderSpecifier = null;
        for (Sort.Order o : pageable.getSort()) {
            Order direction = o.isAscending() ? Order.ASC : Order.DESC;
            if (o.getProperty().equals("createdAt")) {
                orderSpecifier = new OrderSpecifier<>(direction, point.createdAt);
            }
        }
        return orderSpecifier;
    }
}