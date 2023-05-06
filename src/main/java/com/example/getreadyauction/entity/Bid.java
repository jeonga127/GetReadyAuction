package com.example.getreadyauction.entity;

import com.example.getreadyauction.dto.BidRequestDto;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Bid extends Timestamped{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bid_id")
    Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private Users user;

    private int price;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "auction_id", nullable = false)
    private Auction auction;


    public Bid(BidRequestDto bidRequestDto, Users user, Auction auction) {
        this.price = bidRequestDto.getPrice();
        this.user = user;
        this.auction = auction;
    }

    public void Edit(BidRequestDto bidRequestDto){
        this.price = bidRequestDto.getPrice();
    }
}
