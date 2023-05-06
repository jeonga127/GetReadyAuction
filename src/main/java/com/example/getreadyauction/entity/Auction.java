package com.example.getreadyauction.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

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

    @JsonManagedReference
    @OneToMany(mappedBy = "auction", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @OrderBy("createdAt desc")
    private List<Bid> bidList;

    @Builder
    public Auction(String title, String category, String content, int minPrice, String deadline, Users user){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy년 MM월 dd일 HH시 mm분 ss초");

        this.title = title;
        this.category = category;
        this.content = content;
        this.minPrice = minPrice;
        this.currentPrice = minPrice;
        this.deadline = LocalDateTime.parse(deadline,formatter);
        this.isDone = false;
        this.views = 0;
        this.bidSize = 0;
        this.user = user;
    }

    public void setCurrentPrice(int currentPrice){
        this.currentPrice = currentPrice;
    }

    public void addBid(List<Bid> bidList){ this.bidList = bidList;}
}
