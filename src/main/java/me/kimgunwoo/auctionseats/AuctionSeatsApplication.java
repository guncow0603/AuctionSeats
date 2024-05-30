package me.kimgunwoo.auctionseats;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class AuctionSeatsApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuctionSeatsApplication.class, args);
    }

}
