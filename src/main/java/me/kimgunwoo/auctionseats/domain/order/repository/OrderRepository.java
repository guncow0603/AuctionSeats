package me.kimgunwoo.auctionseats.domain.order.repository;

import me.kimgunwoo.auctionseats.domain.order.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order,Long> {
}
