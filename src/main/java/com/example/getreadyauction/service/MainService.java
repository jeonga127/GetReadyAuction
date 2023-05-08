package com.example.getreadyauction.service;


import com.example.getreadyauction.dto.MainAuctionDto;
import com.example.getreadyauction.dto.ResponseDto;
import com.example.getreadyauction.entity.Auction;
import com.example.getreadyauction.repository.AuctionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class MainService {

    private final AuctionRepository auctionRepository;

    @Transactional(readOnly = true) //조회수 기준 실시간 경매리스트
    public ResponseDto<List<MainAuctionDto>> mainView(Pageable pageable) {
        List<Auction> auctionList = auctionRepository.findAllByOrderByViewsDesc(pageable).getContent();
        List<MainAuctionDto> mainAuctionDto = auctionList.stream().map(MainAuctionDto::new).collect(Collectors.toList());
        return ResponseDto.setSuccess("Success : get All Categorized Auctions Information", mainAuctionDto);
    }

    @Transactional(readOnly = true) //마감임박 기준 실시간 경매리스트
    public ResponseDto<List<MainAuctionDto>> mainDeadline( Pageable pageable) {
        List<Auction> auctionList = auctionRepository.findAllByOrderByDeadlineAsc(pageable).getContent();
        List<MainAuctionDto> mainAuctionDto = auctionList.stream().map(MainAuctionDto::new).collect(Collectors.toList());
        return ResponseDto.setSuccess("Success : get All Categorized Auctions Information", mainAuctionDto);
    }


    @Transactional(readOnly = true) //입찰이 많이 된 기준 실시간 경매리스트
    public ResponseDto<List<MainAuctionDto>> mainCount( Pageable pageable) {
        List<Auction> auctionList = auctionRepository.findAllByOrderByBidSizeDesc(pageable).getContent();
        List<MainAuctionDto> mainAuctionDto = auctionList.stream().map(MainAuctionDto::new).collect(Collectors.toList());
        return ResponseDto.setSuccess("Success : get All Categorized Auctions Information", mainAuctionDto);
    }

//    @Transactional(readOnly = true)
//    public ResponseDto<List<MainAuctionDto>> main (String orderBy, Pageable pageable) {
//        List<Auction> auctionList;
//        if (orderBy.equals("views")) {
//            auctionList = auctionRepository.findAllByOrderByViewsAsc(pageable).getContent();
//        } else if (orderBy.equals("deadline")) {
//            auctionList = auctionRepository.findAllByOrderByDeadlineAsc(pageable).getContent();
//        } else if (orderBy.equals("count")) {
//            auctionList = auctionRepository.findAllByOrderByBidSizeAsc(pageable).getContent();
//        } else {
//            throw new IllegalArgumentException();
//        }
//        List<MainAuctionDto> mainAuctionDto = auctionList.stream().map(MainAuctionDto::new).collect(Collectors.toList());
//        return ResponseDto.setSuccess("Success : get All Categorized Auctions Information", mainAuctionDto);
//    }
}
