package com.example.getreadyauction.domain.scheduler.service;

import com.example.getreadyauction.domain.auction.entity.Auction;
import com.example.getreadyauction.domain.auction.repository.AuctionRepository;
import com.example.getreadyauction.domain.bid.entity.Bid;
import com.example.getreadyauction.domain.bid.repository.BidRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class SchedulerService {

    private final AuctionRepository auctionRepository;
    private final BidRepository bidRepository;

    @Async
    @Scheduled(fixedRate = 1000)
    public void check() {
        log.info("check if there are any auctions that should be closed");
        List<Auction> checkList = auctionRepository.findAllByIsDoneOrderByDeadlineDesc(false);
        for(Auction auction : checkList){
            if(auction.getDeadline().isBefore(LocalDateTime.now())){
                Optional<Bid> successBid = bidRepository.findFirstByAuctionOrderByModifiedAtDesc(auction);
                auction.setIsDone();
                auction.setSuccessBid(successBid);
            }
            else return;
        }
    }
}
