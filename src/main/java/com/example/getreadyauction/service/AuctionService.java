package com.example.getreadyauction.service;

import com.example.getreadyauction.dto.AuctionCategoryDto;
import com.example.getreadyauction.dto.AuctionResponseDto;
import com.example.getreadyauction.dto.AuctionSearchDto;
import com.example.getreadyauction.dto.ResponseDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class AuctionService {
    public ResponseDto getAllAuctions() {
        return null;
    }

    public ResponseDto getCategorizedAuctions(AuctionCategoryDto auctionCategoryDto) {
        return null;
    }

    public ResponseDto getDetailedAuctions(Long id) {
        return null;
    }

    public ResponseDto getSearchedAuction(AuctionSearchDto auctionSearchDto) {
        return null;
    }

    @Transactional(readOnly = true) //실시간 전체 조회
    public ResponseDto<AuctionResponseDto> getNowAllAuction(){
        List<Views> viewsList = viewsRepository.findAllByOrderByCreatedAtDesc.stream().map(ResponseDto::new).collect(Collectors.toList());
        List<Deadline> deadlineList = deadlineRepository.findAllByOrderByCreatedAtDesc.stream().map(ResponseDto::new).collect(Collectors.toList());
        List<Bid> countbid = bidRepository.findAllByOrderByCreatedAtDesc().stream().map(ResponseDto::new).collect(Collectors.toList());

        return ResponseDto.setSuccess("Succedd", AuctionResponseDto);
    }
}
