package com.example.getreadyauction.domain.bid.repository;

import com.example.getreadyauction.domain.auction.entity.Auction;
import com.example.getreadyauction.domain.bid.entity.Bid;
import com.example.getreadyauction.domain.user.entity.Users;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BidRepository extends JpaRepository<Bid, Long> {
    Optional<Bid> findByAuctionAndUser(Auction auction, Users users);
    List<Bid> findByAuctionOrderByModifiedAtDesc(Auction auction);
    Optional<Bid> findFirstByAuctionOrderByModifiedAtDesc(Auction auction);
    Page<Bid> findAllByUserOrderByModifiedAtDesc(Pageable pageable, Users users);
}
