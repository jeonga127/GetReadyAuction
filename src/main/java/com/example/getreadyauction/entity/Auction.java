package com.example.getreadyauction.entity;

import com.example.getreadyauction.dto.auction.AuctionRequestDto;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Getter
@NoArgsConstructor
public class Auction extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "auction_id")
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private CategoryType category;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private int minPrice;

    @Column(nullable = false)
    private boolean isDone;

    @Column(nullable = false)
    private int views;

    @Column(nullable = false)
    private int currentPrice;

    @Column(nullable = false)
    private String deadline;

    @Column(nullable = false)
    private int bidSize;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private Users user;

    private String successBid;

    @Builder
    public Auction(AuctionRequestDto auctionRequestDto, Users user) {
        this.title = auctionRequestDto.getTitle();
        this.category = auctionRequestDto.getCategory();
        this.content = auctionRequestDto.getContent();
        this.minPrice = auctionRequestDto.getMinPrice();
        this.currentPrice = auctionRequestDto.getMinPrice();
        this.deadline = auctionRequestDto.getDeadline();
        this.isDone = false;
        this.views = 0;
        this.bidSize = 0;
        this.user = user;
        this.successBid = "낙찰된 사용자가 없습니다.";
    }

    public void setCurrentPrice(int currentPrice) {
        this.currentPrice = currentPrice;
    }

    public void setIsDone(LocalDateTime now) {
        LocalDateTime parsedDeadLine = LocalDateTime.parse(this.deadline, DateTimeFormatter.ofPattern("yyyy년 MM월 dd일 HH시 mm분 ss초"));
        this.isDone = now.isAfter(parsedDeadLine);
    }

    public void setView(int view) {
        this.views += view;
    }

    public void edit(AuctionRequestDto auctionRequestDto) { // deadline : 2023-05-09T19:21:23
        this.title = auctionRequestDto.getTitle();
        this.category = auctionRequestDto.getCategory();
        this.content = auctionRequestDto.getContent();
        this.minPrice = auctionRequestDto.getMinPrice();
        this.deadline = auctionRequestDto.getDeadline();
    }
}
