package com.example.getreadyauction.domain.auction.entity;

import com.example.getreadyauction.domain.auction.dto.AuctionRequestDto;
import com.example.getreadyauction.domain.bid.entity.Bid;
import com.example.getreadyauction.domain.user.entity.Users;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Optional;

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
    private LocalDateTime deadline;

    @Column(nullable = false)
    private int bidSize;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private Users user;

    @Column(nullable = false)
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

    public void setIsDone() {
        this.isDone = true;
    }

    public void setSuccessBid(Optional<Bid> successBid) {
        this.successBid = successBid.isPresent()? successBid.get().getUser().getUsername() : this.successBid;
    }

    public void setBidSize() {
        this.bidSize += 1;
    }

    public void setView(int view) {
        this.views += view;
    }

    public void edit(AuctionRequestDto auctionRequestDto) {
        this.title = auctionRequestDto.getTitle();
        this.category = auctionRequestDto.getCategory();
        this.content = auctionRequestDto.getContent();
        this.minPrice = auctionRequestDto.getMinPrice();
        this.deadline = auctionRequestDto.getDeadline();
    }
}
