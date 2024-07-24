package me.kimgunwoo.auctionseats.domain.user.repository;

import me.kimgunwoo.auctionseats.domain.user.dto.response.PointChargeResponse;
import me.kimgunwoo.auctionseats.domain.user.dto.response.PointResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PointRepositoryCustom {
    /*
     * 해당 유저의 포인트 충전 내역 페이징 리스트 조회
     *
     * @param userId      				유저 id
     * @param pageable    				페이징 정보
     *
     * @return 해당 페이지 정보에 맞는 유저의 포인트 충전 내역 리스트
     * */
    Page<PointChargeResponse> findChargePointListByPage(Long userId, Pageable pageable);

    /*
     * 해당 유저의 포인트 사용/환불 내역 페이징 리스트 조회
     *
     * @param userId      				유저 id
     * @param pageable    				페이징 정보
     *
     * @return 해당 페이지 정보에 맞는 유저의 포인트 사용/환불 내역 리스트
     * */
    Page<PointResponse> findUseAndRefundpointListByPage(Long userId, Pageable pageable);
}