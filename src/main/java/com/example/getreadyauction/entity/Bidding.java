package com.example.getreadyauction.entity;

import com.example.getreadyauction.dto.BiddingRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Bidding extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Integer price;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users user;

    @ManyToOne
    @JoinColumn(name = "auction_id")
    private Auction auction;

    public Bidding(BiddingRequestDto biddingRequestDto, Users user, Auction auction) {
        this.price = biddingRequestDto.getPrice();
        this.user = user;
        this.auction = auction;
    }

    public void Edit(BiddingRequestDto biddingRequestDto){
        this.price = biddingRequestDto.getPrice();
    }
}
