package com.example.getreadyauction.repository;

import com.example.getreadyauction.entity.Auction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuctionRepository extends JpaRepository<Auction, Long> {
    Page<Auction> findAllByCategory(Pageable pageable, String category);
    Page<Auction> findAllByTitleContaining(Pageable pageable, String title);
}
