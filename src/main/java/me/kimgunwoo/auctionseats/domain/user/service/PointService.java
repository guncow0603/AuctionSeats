package me.kimgunwoo.auctionseats.domain.user.service;

import me.kimgunwoo.auctionseats.domain.user.dto.response.PointChargeResponse;
import me.kimgunwoo.auctionseats.domain.user.entity.User;
import org.springframework.data.domain.Page;
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
    void chargePoint(User user, Long point, String orderId);

    /*
     * 해당 유저의 포인트 사용 내역 페이징 리스트 조회
     *
     * @param user			로그인 인증 유저
     * @param pageable    	페이징 조건
     *
     * @return 				페이징 된 포인트 충전 내역
     * */
    Page<PointChargeResponse> getChargePointLogList(User user, Pageable pageable);
}
