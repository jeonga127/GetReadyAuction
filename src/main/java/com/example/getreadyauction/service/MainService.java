package com.example.getreadyauction.service;


import com.example.getreadyauction.dto.ResponseDto;
import com.example.getreadyauction.dto.auction.AuctionResponseDto;
import com.example.getreadyauction.entity.Auction;
import com.example.getreadyauction.repository.AuctionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
@Transactional
public class MainService {

    private final AuctionRepository auctionRepository;

    @Transactional(readOnly = true)
    public ResponseDto<List<AuctionResponseDto>> mainView(Pageable pageable) {
        List<Auction> tmpView = auctionRepository.findAllByIsDoneOrderByViewsDesc(false, pageable).getContent();
        Stream<Auction> viewList = tmpView.size() < 2 ? tmpView.stream() : tmpView.subList(0,2).stream();

        List<Auction> tmpDeadline = auctionRepository.findAllByIsDoneOrderByDeadlineAsc(false, pageable).getContent();
        Stream<Auction> deadlineList = tmpDeadline.size() < 2 ? tmpDeadline.stream() : tmpDeadline.subList(0,2).stream();

        List<Auction> tmpBidSize = auctionRepository.findAllByIsDoneOrderByBidSizeDesc(false, pageable).getContent();
        Stream<Auction> bidSizeList = tmpBidSize.size() < 2 ? tmpBidSize.stream() : tmpBidSize.subList(0,2).stream();

        List<Auction> auctionList = Stream.concat(viewList, Stream.concat(deadlineList,bidSizeList)).toList();

        List<AuctionResponseDto> auctionResponseDto = auctionList.stream().map(AuctionResponseDto::new).collect(Collectors.toList());
        return ResponseDto.setSuccess("Success : get All Categorized Auctions Information", auctionResponseDto);
    }
}
