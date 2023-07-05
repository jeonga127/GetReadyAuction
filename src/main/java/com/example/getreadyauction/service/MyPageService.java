package com.example.getreadyauction.service;


import com.example.getreadyauction.dto.auction.AuctionResponseDto;
import com.example.getreadyauction.entity.Auction;
import com.example.getreadyauction.entity.Users;
import com.example.getreadyauction.repository.AuctionRepository;
import com.example.getreadyauction.repository.BidRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MyPageService {

    private final AuctionRepository auctionRepository;
    private final BidRepository bidRepository;

    @Transactional(readOnly = true)
    public ResponseEntity<List<AuctionResponseDto>> getAllMyAuctions(Pageable pageable, Users user) {
        Page<Auction> auctionPage = auctionRepository.findAllByUserOrderByModifiedAtDesc(pageable, user);
        List<AuctionResponseDto> auctionResponseDtoList =
                auctionPage.getContent()
                .stream()
                .map(AuctionResponseDto::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(auctionResponseDtoList);
    }

    @Transactional(readOnly = true)
    public ResponseEntity<List<AuctionResponseDto>> getAllMyBids(Pageable pageable, Users user) {
        List<AuctionResponseDto> auctionResponseDtoList =
                bidRepository.findAllByUserOrderByModifiedAtDesc(pageable, user)
                .getContent()
                .stream()
                .map(bid -> new AuctionResponseDto(bid.getAuction()))
                .collect(Collectors.toList());
        return ResponseEntity.ok(auctionResponseDtoList);
    }
}