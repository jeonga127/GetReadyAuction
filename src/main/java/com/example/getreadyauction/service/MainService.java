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

@Service
@RequiredArgsConstructor
@Transactional
public class MainService {

    private final AuctionRepository auctionRepository;

    @Transactional(readOnly = true)
    public ResponseDto<List<AuctionResponseDto>> main (String orderBy, Pageable pageable) {
        List<Auction> auctionList;
        if (orderBy.equals("views")) { //조회수
            auctionList = auctionRepository.findAllByIsDoneOrderByViewsDesc(false, pageable).getContent();
        } else if (orderBy.equals("deadline")) { //마감입박
            auctionList = auctionRepository.findAllByIsDoneOrderByDeadlineAsc(false, pageable).getContent();
        } else if (orderBy.equals("count")) { //입찰
            auctionList = auctionRepository.findAllByIsDoneOrderByBidSizeDesc(false, pageable).getContent();
        } else {
            throw new IllegalArgumentException("else Massage");
        }
        List<AuctionResponseDto> auctionResponseDto = auctionList.stream().map(AuctionResponseDto::new).collect(Collectors.toList());
        return ResponseDto.setSuccess("Success : get All Categorized Auctions Information", auctionResponseDto);
    }
}
