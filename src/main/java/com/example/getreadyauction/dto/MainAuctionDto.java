package com.example.getreadyauction.dto;

import com.example.getreadyauction.entity.Auction;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class MainAuctionDto {
    private Long id;
    private String title;
    private String category;
    private int currentPrice;
    private LocalDateTime deadLine;


    public MainAuctionDto(Auction action) {
        this.id = action.getId();
        this.title = action.getTitle();
        this.category = action.getCategory();
        this.currentPrice = action.getCurrentPrice();
        this.deadLine = action.getDeadline();
    }
}
