package com.example.getreadyauction.domain.auction.dto;

import com.example.getreadyauction.domain.auction.entity.CategoryType;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class AuctionRequestDto {

    private String title;
    private CategoryType category;
    private String content;
    private int minPrice;
    private LocalDateTime deadline;

    @Builder
    public AuctionRequestDto(String title, CategoryType category, String content, int minPrice, LocalDateTime deadline) {
        this.title = title;
        this.category = category;
        this.content = content;
        this.minPrice = minPrice;
        this.deadline = deadline;
    }
}
