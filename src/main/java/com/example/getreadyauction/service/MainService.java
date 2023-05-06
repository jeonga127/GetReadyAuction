package com.example.getreadyauction.service;


import com.example.getreadyauction.dto.MainAuctionDto;
import com.example.getreadyauction.dto.ResponseDto;
import com.example.getreadyauction.entity.Auction;
import com.example.getreadyauction.repository.AuctionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class MainService {


    private final AuctionRepository auctionRepository;
    @Transactional(readOnly = true)//조회수 기준 실시간 경매리스트
    public ResponseDto mainView(Pageable pageable) {
        List<Auction> auctionList = auctionRepository.findAllByOrderByViewsDesc(pageable).getContent(); //페이징
        List<MainAuctionDto>mainAuctionDto= new ArrayList<>(); //Dto선언

        for (Auction auction : auctionList) {
            MainAuctionDto mainResponseDto = new MainAuctionDto(auction);
            mainAuctionDto.add(mainResponseDto);
        }
        return ResponseDto.setSuccess("Success : get All Auctions Information", mainAuctionDto);
    }


    @Transactional(readOnly = true)//마감임박 기준 실시간 경매리스트 mainDeadline
    public ResponseDto mainDeadline(Pageable pageable) {
        List<Auction> auctionList = auctionRepository.findAllByOrderByDeadlineDesc(pageable).getContent(); //페이징
        List<MainAuctionDto>mainAuctionDto= new ArrayList<>(); //Dto선언

        for (Auction auction : auctionList) {
            MainAuctionDto mainResponseDto = new MainAuctionDto(auction);
            mainAuctionDto.add(mainResponseDto);
        }
        return ResponseDto.setSuccess("Success : get All Auctions Information", mainAuctionDto);
    }


    @Transactional(readOnly = true)// 입찰이 제일 많이 된 실시간 경매리스트
    public ResponseDto mainCount(Pageable pageable) {
        List<Auction> auctionList = auctionRepository.findAllByOrderByBidSizeDesc(pageable).getContent(); //페이징
        List<MainAuctionDto>mainAuctionDto= new ArrayList<>(); //Dto선언

        for (Auction auction : auctionList) {
            MainAuctionDto mainResponseDto = new MainAuctionDto(auction);
            mainAuctionDto.add(mainResponseDto);
        }
        return ResponseDto.setSuccess("Success : get All Auctions Information", mainAuctionDto);
    }

}
