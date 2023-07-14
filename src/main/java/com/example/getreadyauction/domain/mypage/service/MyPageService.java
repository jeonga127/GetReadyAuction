package com.example.getreadyauction.domain.mypage.service;


import com.example.getreadyauction.domain.auction.dto.AuctionResponseDto;
import com.example.getreadyauction.domain.user.entity.Users;
import com.example.getreadyauction.domain.auction.repository.AuctionRepository;
import com.example.getreadyauction.domain.bid.repository.BidRepository;
import lombok.RequiredArgsConstructor;
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
        List<AuctionResponseDto> auctionResponseDtoList =
                auctionRepository
                        .findAllByUserOrderByModifiedAtDesc(pageable, user)
                        .getContent()
                        .stream()
                        .map(AuctionResponseDto::new)
                        .collect(Collectors.toList());
        return ResponseEntity.ok(auctionResponseDtoList);
    }

    @Transactional(readOnly = true)
    public ResponseEntity<List<AuctionResponseDto>> getAllMyBids(Pageable pageable, Users user) {
        List<AuctionResponseDto> auctionResponseDtoList =
                bidRepository
                        .findAllByUserOrderByModifiedAtDesc(pageable, user)
                        .getContent()
                        .stream()
                        .map(bid -> new AuctionResponseDto(bid.getAuction()))
                        .collect(Collectors.toList());
        return ResponseEntity.ok(auctionResponseDtoList);
    }

    @Transactional(readOnly = true)
    public ResponseEntity<List<AuctionResponseDto>> getAllAwardedAuctions(Pageable pageable, Users user) {
        List<AuctionResponseDto> auctionResponseDtoList =
                auctionRepository
                        .findAllBySuccessBidAndIsDoneOrderByModifiedAtDesc(pageable, user.getUsername(), true)
                        .getContent()
                        .stream()
                        .map(AuctionResponseDto::new)
                        .collect(Collectors.toList());
        return ResponseEntity.ok(auctionResponseDtoList);
    }
}