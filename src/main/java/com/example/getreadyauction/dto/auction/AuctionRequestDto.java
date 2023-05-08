package com.example.getreadyauction.dto.auction;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AuctionRequestDto {

    private String title;
    private String category;
    private String content;
    private Integer minPrice;
    private String deadline;
}
