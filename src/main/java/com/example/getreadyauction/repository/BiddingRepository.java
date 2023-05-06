package com.example.getreadyauction.repository;

import com.example.getreadyauction.entity.Auction;
import com.example.getreadyauction.entity.Bidding;
import com.example.getreadyauction.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BiddingRepository extends JpaRepository<Bidding, Long> {

    Optional<Bidding> findByAuctionAndUser(Auction auction, Users users);
    Optional<Bidding> findByAuction(Auction auction);
}
