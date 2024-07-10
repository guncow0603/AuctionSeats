//package me.kimgunwoo.auctionseats.domain.reservation.service;
//
//
//import me.kimgunwoo.auctionseats.domain.reservation.dto.request.ReservationCreateRequest;
//import me.kimgunwoo.auctionseats.domain.reservation.dto.response.ReservationDetailResponse;
//import me.kimgunwoo.auctionseats.domain.user.entity.User;
//
//public interface ReservationService {
//
//    /**
//     * 1개 좌석을 예매한다.
//     *
//     * @param user       실행 유저
//     * @param seatId     예매할 좌석
//     * @param sequenceId 예매할 공연의 회차
//     * @param request    예매 요청 정보
//     * @return 예매한 좌석의 정보를 반환한다.
//     */
//    ReservationDetailResponse reserve(
//            User user,
//            Long seatId,
//            Long sequenceId,
//            ReservationCreateRequest request
//    );
//}