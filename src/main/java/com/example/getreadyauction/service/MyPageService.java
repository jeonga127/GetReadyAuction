package com.example.getreadyauction.service;

import com.example.getreadyauction.dto.AuctionResponseDto;
import com.example.getreadyauction.dto.ResponseDto;
import com.example.getreadyauction.entity.Auction;
import com.example.getreadyauction.entity.Bid;
import com.example.getreadyauction.entity.Users;
import com.example.getreadyauction.repository.AuctionRepository;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MyPageService {

    private final AuctionRepository auctionRepository;

    @Transactional(readOnly = true) //내 경매 전체 조회
    public ResponseDto<List<AuctionResponseDto>> myAuctions(Pageable pageable, Users user) {
        Page<Auction> auctionPage = auctionRepository.findAllByUser(pageable, user); //페이징
        List<AuctionResponseDto> auctionResponseDtoList = auctionPage.getContent().stream().map(AuctionResponseDto::new).collect(Collectors.toList());
        return ResponseDto.setSuccess("Success : get All Auctions Information", auctionResponseDtoList);
    }



    @Transactional(readOnly = true) //내 입찰 전체 조회
    public ResponseDto<List<AuctionResponseDto>> myBid(Pageable pageable, Users user) {
        Page<Auction> auctionPage = auctionRepository.findByBidListUser(pageable, user); //페이징
        List<AuctionResponseDto> auctionResponseDtoList = auctionPage.getContent().stream().map(AuctionResponseDto::new).collect(Collectors.toList());
        return ResponseDto.setSuccess("Success : get All Auctions Information", auctionResponseDtoList);
    }
}