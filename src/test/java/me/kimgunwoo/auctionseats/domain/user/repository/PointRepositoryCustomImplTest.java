package me.kimgunwoo.auctionseats.domain.user.repository;

import me.kimgunwoo.auctionseats.domain.user.dto.response.PointChargeResponse;
import me.kimgunwoo.auctionseats.domain.user.entity.Point;
import me.kimgunwoo.auctionseats.domain.user.entity.User;
import me.kimgunwoo.auctionseats.domain.user.enums.PointType;
import me.kimgunwoo.auctionseats.domain.user.util.UserUtil;
import me.kimgunwoo.auctionseats.global.config.AuditingConfig;
import me.kimgunwoo.auctionseats.global.config.QueryDslConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;



@SpringBootTest
@Import({QueryDslConfig.class, AuditingConfig.class})
@ActiveProfiles("test")
class PointRepositoryCustomImplTest {

    @Autowired
    PointRepository pointRepository;
    @Autowired
    UserRepository userRepository;

    private static Point createPoint(Long id, Long changePoint, int day) {
        PointType type = PointType.CHARGE;
        if (day % 3 == 1)
            type = PointType.USE;
        else if (day % 3 == 2)
            type = PointType.REFUND;

        Point point = Point.builder()
                .changePoint(changePoint)
                .orderId(UUID.randomUUID().toString())
                .type(type)
                .user(UserUtil.getUser())
                .build();

        ReflectionTestUtils.setField(point, "id", id);
        ReflectionTestUtils.setField(point, "createdAt", LocalDateTime.now().plusDays(day));

        return point;
    }

    @BeforeEach
    void beforeEach() {
        userRepository.save(UserUtil.getUser());

        for (int i = 1; i <= 12; i++) {
            pointRepository.save(createPoint((long)i, (long)(1111 * (i + 1)), i));
        }
    }
}