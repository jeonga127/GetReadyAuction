package com.example.getreadyauction.domain.auction.dto;

import com.example.getreadyauction.domain.auction.entity.Auction;
import com.example.getreadyauction.domain.bid.entity.Bid;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
public class AuctionAllResponseDto {
    private Long id;
    private String title;
    private String category;
    private String username;
    private String content;
    private String successBid;
    private LocalDateTime deadline;
    private LocalDateTime modifiedAt;
    private List<Bid> bidList;
    private boolean isDone;
    private int minPrice;
    private int currentPrice;
    private int views;

    public AuctionAllResponseDto(Auction auction, List<Bid> bidList){
        this.id = auction.getId();
        this.title = auction.getTitle();
        this.category = auction.getCategory().getText();
        this.username = auction.getUser().getUsername();
        this.content = auction.getContent();
        this.deadline = auction.getDeadline();
        this.modifiedAt = auction.getModifiedAt();
        this.bidList = bidList;
        this.isDone = auction.isDone();
        this.minPrice = auction.getMinPrice();
        this.currentPrice = auction.getCurrentPrice();
        this.views = auction.getViews();
        this.successBid = auction.getSuccessBid();
    }
}
