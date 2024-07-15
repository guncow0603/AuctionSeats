package me.kimgunwoo.auctionseats.domain.user.service;

import lombok.RequiredArgsConstructor;
import me.kimgunwoo.auctionseats.domain.user.entity.Point;
import me.kimgunwoo.auctionseats.domain.user.entity.User;
import me.kimgunwoo.auctionseats.domain.user.enums.PointType;
import me.kimgunwoo.auctionseats.domain.user.repository.PointRepository;
import me.kimgunwoo.auctionseats.global.exception.ApiException;
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
    public void refundPoint(User user, Long point) {
        Point refundPoint = Point.builder()
                .changePoint(point)
                .user(user)
                .type(PointType.REFUND)
                .build();

        user.addPoint(point);
        pointRepository.save(refundPoint);
    }

    @Override
    @Transactional
    public void chargePoint(User user, Long point) {
        Point chargePoint = Point.builder()
                .changePoint(point)
                .user(user)
                .type(PointType.CHARGE)
                .build();

        user.addPoint(point);
        pointRepository.save(chargePoint);
    }
}