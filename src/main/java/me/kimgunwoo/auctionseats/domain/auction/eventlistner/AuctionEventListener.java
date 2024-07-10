package me.kimgunwoo.auctionseats.domain.auction.eventlistner;

import lombok.RequiredArgsConstructor;
import me.kimgunwoo.auctionseats.domain.auction.service.AuctionService;
import me.kimgunwoo.auctionseats.global.event.AuctionEndEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuctionEventListener {
    private final AuctionService auctionService;

    @Async
    @EventListener
    public void listen(AuctionEndEvent auctionEndEvent) {
        auctionService.endAuction(auctionEndEvent.getId());
    }
}