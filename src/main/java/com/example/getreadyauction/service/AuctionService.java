package com.example.getreadyauction.service;

import com.example.getreadyauction.dto.AuctionCategoryDto;
import com.example.getreadyauction.dto.AuctionResponseDto;
import com.example.getreadyauction.dto.AuctionSearchDto;
import com.example.getreadyauction.dto.ResponseDto;
import com.example.getreadyauction.dto.*;
import com.example.getreadyauction.entity.Auction;
import com.example.getreadyauction.entity.Bid;
import com.example.getreadyauction.repository.AuctionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class AuctionService {

    private final AuctionRepository auctionRepository;

    @Transactional(readOnly = true)
    public ResponseDto<List<AuctionResponseDto>> getAllAuctions(Pageable pageable) {
        List<Auction> auctionList = auctionRepository.findAll(pageable).getContent();
        List<AuctionResponseDto> auctionResponseDtoList = auctionList.stream().map(AuctionResponseDto::new).collect(Collectors.toList());
        return ResponseDto.setSuccess("Success : get All Auctions Information", auctionResponseDtoList);
    }

    @Transactional(readOnly = true)
    public ResponseDto<List<AuctionResponseDto>> getCategorizedAuctions(Pageable pageable, AuctionCategoryDto auctionCategoryDto) {
        List<Auction> auctionList = auctionRepository.findAllByCategory(pageable, auctionCategoryDto.getCategory()).getContent();
        List<AuctionResponseDto> auctionResponseDtoList = auctionList.stream().map(AuctionResponseDto::new).collect(Collectors.toList());
        return ResponseDto.setSuccess("Success : get All Categorized Auctions Information", auctionResponseDtoList);
    }

    @Transactional(readOnly = true)
    public ResponseDto<List<AuctionResponseDto>> getSearchedAuction(Pageable pageable, AuctionSearchDto auctionSearchDto) {
        List<Auction> auctionList = auctionRepository.findAllByTitleContaining(pageable, auctionSearchDto.getSearch()).getContent();
        List<AuctionResponseDto> auctionResponseDtoList = auctionList.stream().map(AuctionResponseDto::new).collect(Collectors.toList());
        return ResponseDto.setSuccess("Success : get All Categorized Auctions Information", auctionResponseDtoList);
    }

    @Transactional(readOnly = true)
    public ResponseDto<AuctionAllResponseDto> getDetailedAuctions(Long id) {
        Auction auction = validateAuction(id);
        List<Bid> tmpList = new ArrayList<>(List.copyOf(auction.getBidList()));
        tmpList.sort((o1, o2) -> {
            if (o1.getPrice() < o2.getPrice())
                return 1;
            else if (o1.getPrice() > o2.getPrice())
                return -1;
            else return 0;
        });
        List<Bid> topBidList = tmpList.stream().skip(3).limit(3).collect(Collectors.toList());
        return ResponseDto.setSuccess("Success : get Detailed Auction Information", new AuctionAllResponseDto(auction, topBidList));
    }

    @Transactional(readOnly = true) //실시간 전체 조회
    public ResponseDto<AuctionResponseDto> getNowAllAuction(){
        List<Views> viewsList = viewsRepository.findAllByOrderByCreatedAtDesc.stream().map(ResponseDto::new).collect(Collectors.toList());
        List<Deadline> deadlineList = deadlineRepository.findAllByOrderByCreatedAtDesc.stream().map(ResponseDto::new).collect(Collectors.toList());
        List<Bid> countbid = bidRepository.findAllByOrderByCreatedAtDesc().stream().map(ResponseDto::new).collect(Collectors.toList());

        return ResponseDto.setSuccess("Succedd", AuctionResponseDto);
    }

    public Auction validateAuction(Long id){
        return auctionRepository.findById(id).orElseThrow(() -> new NoSuchElementException("존재하지 않는 경매입니다."));
    }
}
