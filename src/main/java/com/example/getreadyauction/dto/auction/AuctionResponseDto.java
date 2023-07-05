package com.example.getreadyauction.dto.auction;

import com.example.getreadyauction.entity.Auction;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AuctionResponseDto {
    private Long id;
    private String title;
    private String category;
    private int currentPrice;
    private String deadline;

    public AuctionResponseDto(Auction auction){
        this.id = auction.getId();
        this.title = auction.getTitle();
        this.category = auction.getCategory().getText();
        this.currentPrice = auction.getCurrentPrice();
        this.deadline = auction.getDeadline();
    }
}
