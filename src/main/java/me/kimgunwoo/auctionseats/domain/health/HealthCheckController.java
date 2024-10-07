package me.kimgunwoo.auctionseats.domain.health;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthCheckController {

    @GetMapping("/health")
    public String health() {
        return "OK"; // 상태가 정상일 때의 응답
    }
}