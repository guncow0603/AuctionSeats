package me.kimgunwoo.auctionseats.domain.auction.repository;

import me.kimgunwoo.auctionseats.domain.auction.dto.response.AuctionInfoResponse;
import me.kimgunwoo.auctionseats.domain.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

import java.util.List;

import static me.kimgunwoo.auctionseats.domain.auction.entity.QAuction.auction;
import static me.kimgunwoo.auctionseats.domain.bid.entity.QBid.bid;

@Repository
public class AuctionRepositoryCustomImpl implements AuctionRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;

    public AuctionRepositoryCustomImpl(JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
    }

    @Override
    public Page<AuctionInfoResponse> getJoinedMyAuctions(User user, Pageable pageable) {
        List<AuctionInfoResponse> myAuctions = jpaQueryFactory
                .select(Projections.constructor(AuctionInfoResponse.class,
                        auction.id,
                        auction.isEnded,
                        auction.startDateTime
                ))
                .from(auction)
                .join(bid).on(auction.id.eq(bid.auction.id))
                .where(bid.user.eq(user))
                .distinct()
                .orderBy(sortAuction(pageable))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> countQuery = jpaQueryFactory.select(auction.count())
                .from(auction);

        //getPage -> count 쿼리 지연
        // 1. 첫 번째 페이지이면서 content 크기가 한 페이지의 사이즈보다 작을 때 (ex, content:3개, page 크기: 10)
        // 2. 마지막 페이지일 때 (getOffset이 0이 아니면서, content 크기가 한 페이지의 사이즈보다 작을 때)
        return PageableExecutionUtils.getPage(myAuctions, pageable, countQuery::fetchOne);
    }

    private OrderSpecifier<?> sortAuction(Pageable pageable) {
        OrderSpecifier<?> orderSpecifier = null;
        for (Sort.Order o : pageable.getSort()) {
            Order direction = o.isAscending() ? Order.ASC : Order.DESC;
            if (o.getProperty().equals("startDateTime")) {
                orderSpecifier = new OrderSpecifier<>(direction, auction.startDateTime);
            }
        }
        return orderSpecifier;
    }
}