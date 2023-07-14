package com.example.getreadyauction.domain.auction.repository;

import com.example.getreadyauction.domain.auction.entity.Auction;
import com.example.getreadyauction.domain.auction.entity.CategoryType;
import com.example.getreadyauction.domain.user.entity.Users;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AuctionRepository extends JpaRepository<Auction, Long> {
    Page<Auction> findAllByOrderByCreatedAtDesc(Pageable pageable);
    Page<Auction> findAllByCategoryOrderByModifiedAtDesc(Pageable pageable, CategoryType category);
    Page<Auction> findAllByTitleContainingOrderByModifiedAtDesc(Pageable pageable, String title);
    Page<Auction> findAllByUserOrderByModifiedAtDesc(Pageable pageable, Users user);
    Page<Auction> findAllBySuccessBidAndIsDoneOrderByModifiedAtDesc(Pageable pageable, String username, boolean isDone);
    List<Auction> findAllByIsDoneOrderByDeadlineDesc(boolean isDone);
}
