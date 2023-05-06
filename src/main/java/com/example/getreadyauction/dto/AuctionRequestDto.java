package com.example.getreadyauction.dto;

import com.example.getreadyauction.entity.Auction;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@NoArgsConstructor
public class AuctionRequestDto {

    private String title;
    private String category;
    private String content;
    private Integer minPrice;
    private String deadline;

}
