package me.kimgunwoo.auctionseats.domain.user.service;

import me.kimgunwoo.auctionseats.domain.user.entity.User;
import org.springframework.data.domain.Pageable;

public interface PointService {

    /*
     * 포인트 환불
     *
     * @param user		포인트를 환불받는 회원
     * @param point		환불될 포인트 양
     * */
    void refundPoint(User user, Long point);

    /*
     * 포인트 사용
     *
     * @param user		포인트를 사용하는 회원
     * @param point		사용할 포인트 양
     * */
    void usePoint(User user, Long point);

    /*
     * 포인트 충전
     *
     * @param user		포인트를 충전하는 회원
     * @param point		충전할 포인트 양
     * @param orderId	결제 내역 id
     * */
    void chargePoint(User user, Long point);

}
