package me.kimgunwoo.auctionseats.domain.auction.repository;

import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import me.kimgunwoo.auctionseats.domain.auction.dto.response.AuctionInfoResponse;
import me.kimgunwoo.auctionseats.domain.auction.entity.Auction;
import me.kimgunwoo.auctionseats.domain.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

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

        return PageableExecutionUtils.getPage(myAuctions, pageable, countQuery::fetchOne);
    }

    @Override
    public boolean exists(Auction pAuction) {
        Integer result = jpaQueryFactory.selectOne().from(auction)
                .where(searchAuction(pAuction))
                .fetchFirst();
        return result != null;
    }

    @Override
    public Optional<Auction> findBySeatInfo(Long scheduleId, Long zoneGradeId, Integer seatNumber) {
        Auction auction1 = jpaQueryFactory
                .select(auction)
                .from(auction)
                .where(auction.schedule.id.eq(scheduleId),
                        auction.zoneGrade.id.eq(zoneGradeId),
                        auction.seatNumber.eq(seatNumber))
                .fetchOne();
        return Optional.ofNullable(auction1);
    }

    private Predicate searchAuction(Auction pAuction) {
        return auction.seatNumber.eq(pAuction.getSeatNumber())
                .and(auction.zoneGrade.eq(pAuction.getZoneGrade()))
                .and(auction.schedule.eq(pAuction.getSchedule()));
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
