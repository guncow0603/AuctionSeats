package me.kimgunwoo.auctionseats.domain.user.service;

import lombok.RequiredArgsConstructor;
import me.kimgunwoo.auctionseats.domain.user.dto.response.PointChargeResponse;
import me.kimgunwoo.auctionseats.domain.user.dto.response.PointUseResponse;
import me.kimgunwoo.auctionseats.domain.user.entity.Point;
import me.kimgunwoo.auctionseats.domain.user.entity.User;
import me.kimgunwoo.auctionseats.domain.user.enums.PointType;
import me.kimgunwoo.auctionseats.domain.user.repository.PointRepository;
import me.kimgunwoo.auctionseats.global.exception.ApiException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static me.kimgunwoo.auctionseats.global.exception.ErrorCode.NOT_ENOUGH_POINT;
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PointServiceImpl implements PointService {

    private final PointRepository pointRepository;

    @Override
    @Transactional
    public void usePoint(User user, Long point) {
        if (user.getPoint() < point) {
            throw new ApiException(NOT_ENOUGH_POINT);
        }

        Point usePoint = Point.builder()
                .changePoint(point)
                .user(user)
                .type(PointType.USE)
                .build();

        user.usePoint(point);
        pointRepository.save(usePoint);
    }

    @Override
    @Transactional
    public void chargePoint(User user, Long point, String orderId) {
        Point chargePoint = Point.builder()
                .changePoint(point)
                .user(user)
                .type(PointType.CHARGE)
                .orderId(orderId)
                .build();

        user.chargePoint(point);
        pointRepository.save(chargePoint);
    }

    @Override
    public Page<PointChargeResponse> getChargePointLogList(User loginUser, Pageable pageable) {
        return pointRepository.findChargePointListByPage(loginUser.getId(), pageable);
    }

    @Override
    public Page<PointUseResponse> getUsePointLogList(User user, Pageable pageable) {
        return pointRepository.findUsePointListByPage(user.getId(), pageable);
    }
}