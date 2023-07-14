package com.example.getreadyauction.domain.auction.dto;

import com.example.getreadyauction.domain.auction.entity.Auction;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class AuctionResponseDto {
    private Long id;
    private String title;
    private String category;
    private int currentPrice;
    private LocalDateTime deadline;

    public AuctionResponseDto(Auction auction){
        this.id = auction.getId();
        this.title = auction.getTitle();
        this.category = auction.getCategory().getText();
        this.currentPrice = auction.getCurrentPrice();
        this.deadline = auction.getDeadline();
    }
}
