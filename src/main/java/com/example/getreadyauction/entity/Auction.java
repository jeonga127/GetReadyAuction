package com.example.getreadyauction.entity;

import com.example.getreadyauction.dto.AuctionRequestDto;
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
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String category;

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
    private LocalDateTime deadline;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private Users user;

    @Builder
    public Auction(AuctionRequestDto auctionRequestDto, Users user){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy년 MM월 dd일 HH시 mm분 ss초");

        this.title = auctionRequestDto.getTitle();
        this.category = auctionRequestDto.getCategory();
        this.content = auctionRequestDto.getContent();
        this.minPrice = auctionRequestDto.getMinPrice();
        this.currentPrice = auctionRequestDto.getMinPrice();
        this.deadline = LocalDateTime.parse(auctionRequestDto.getDeadline(), formatter);
        this.isDone = false;
        this.views = 0;
        this.user = user;
    }

    public void setCurrentPrice(int currentPrice){
        this.currentPrice = currentPrice;
    }

    public void Edit(AuctionRequestDto auctionRequestDto) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy년 MM월 dd일 HH시 mm분 ss초");
        this.title = auctionRequestDto.getTitle();
        this.category = auctionRequestDto.getCategory();
        this.content = auctionRequestDto.getContent();
        this.minPrice = auctionRequestDto.getMinPrice();
        this.deadline = LocalDateTime.parse(auctionRequestDto.getDeadline(), formatter);
    }

    public void Up(Auction auction){
        this.title = auction.getTitle();
        this.category = auction.getCategory();
        this.content = auction.getContent();
        this.minPrice = auction.getMinPrice();
        this.deadline = auction.getDeadline();
    }
}
