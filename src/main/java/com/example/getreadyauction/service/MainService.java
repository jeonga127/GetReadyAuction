package com.example.getreadyauction.service;


import com.example.getreadyauction.dto.MainAuctionDto;
import com.example.getreadyauction.dto.ResponseDto;
import com.example.getreadyauction.dto.auction.AuctionCategoryDto;
import com.example.getreadyauction.dto.auction.AuctionResponseDto;
import com.example.getreadyauction.entity.Auction;
import com.example.getreadyauction.repository.AuctionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class MainService {


    private final AuctionRepository auctionRepository;

    @Transactional(readOnly = true) //조회수 기준 실시간 경매리스트
    public ResponseDto<List<MainAuctionDto>> mainView(Pageable pageable) {
        List<Auction> auctionList = auctionRepository.findAllByOrderByViewsAsc(pageable).getContent();
        List<MainAuctionDto> mainAuctionDto = auctionList.stream().map(MainAuctionDto::new).collect(Collectors.toList());
        return ResponseDto.setSuccess("Success : get All Categorized Auctions Information", mainAuctionDto);
    }


    @Transactional(readOnly = true) //마감임박 기준 실시간 경매리스트
    public ResponseDto<List<MainAuctionDto>> mainDeadline( Pageable pageable) {
        List<Auction> auctionList = auctionRepository.findAllByOrderByDeadlineAsc(pageable).getContent();
        List<MainAuctionDto> mainAuctionDto = auctionList.stream().map(MainAuctionDto::new).collect(Collectors.toList());
        return ResponseDto.setSuccess("Success : get All Categorized Auctions Information", mainAuctionDto);
    }


    @Transactional(readOnly = true) //마감임박 기준 실시간 경매리스트
    public ResponseDto<List<MainAuctionDto>> mainCount( Pageable pageable) {
        List<Auction> auctionList = auctionRepository.findAllByOrderByBidSizeAsc(pageable).getContent();
        List<MainAuctionDto> mainAuctionDto = auctionList.stream().map(MainAuctionDto::new).collect(Collectors.toList());
        return ResponseDto.setSuccess("Success : get All Categorized Auctions Information", mainAuctionDto);
    }

}
