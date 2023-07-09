package com.example.getreadyauction.repository;

import com.example.getreadyauction.entity.Auction;
import com.example.getreadyauction.entity.CategoryType;
import com.example.getreadyauction.entity.Users;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuctionRepository extends JpaRepository<Auction, Long> {
    Page<Auction> findAllByOrderByCreatedAtDesc(Pageable pageable);
    Page<Auction> findAllByCategoryOrderByModifiedAtDesc(Pageable pageable, CategoryType category);
    Page<Auction> findAllByTitleContainingOrderByModifiedAtDesc(Pageable pageable, String title);
    Page<Auction> findAllByUserOrderByModifiedAtDesc(Pageable pageable, Users user);
    Page<Auction> findAllBySuccessBidAndIsDoneOrderByModifiedAtDesc(Pageable pageable, String username, boolean isDone);
}
