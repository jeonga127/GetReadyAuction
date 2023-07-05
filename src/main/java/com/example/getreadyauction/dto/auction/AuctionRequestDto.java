package com.example.getreadyauction.dto.auction;

import com.example.getreadyauction.entity.CategoryType;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AuctionRequestDto {

    private String title;
    private CategoryType category;
    private String content;
    private int minPrice;
    private String deadline;

    @Builder
    public AuctionRequestDto(String title, CategoryType category, String content, int minPrice, String deadline) {
        this.title = title;
        this.category = category;
        this.content = content;
        this.minPrice = minPrice;
        this.deadline = deadline;
    }
}
