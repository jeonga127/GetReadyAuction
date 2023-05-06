package com.example.getreadyauction.service;

import com.example.getreadyauction.dto.AuctionCategoryDto;
import com.example.getreadyauction.dto.AuctionRequestDto;
import com.example.getreadyauction.dto.AuctionSearchDto;
import com.example.getreadyauction.dto.ResponseDto;
import com.example.getreadyauction.entity.Auction;
import com.example.getreadyauction.entity.Users;
import com.example.getreadyauction.repository.AuctionRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuctionService {

    private final AuctionRepository auctionRepository;


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

    public ResponseDto postAddAuction(AuctionRequestDto auctionRequestDto, Users users){
        Auction auction = new Auction(auctionRequestDto, users);
        auctionRepository.saveAndFlush(auction);
        return ResponseDto.setSuccess("물품이 등록되었습니다", null);
    }

    @Transactional
    public ResponseDto putEditAuction(Long id, AuctionRequestDto auctionRequestDto, Users users){
        Auction auction = auctionRepository.findById(id).orElseThrow(
                () -> new NullPointerException("존재하지 않는 물품입니다")  // null 처리
        );
        if (users.getUsername().equals(auction.getUser().getUsername())){ // 게시글의 아이디와 유저의 아이디 비교
            auction.Edit(auctionRequestDto);
        } else
            throw new IllegalArgumentException("권한이 없습니다");
        return ResponseDto.setSuccess("물품이 수정되었습니다", null);
    }
    @Transactional
    public ResponseDto putUpAuction(Long id, Users users){
        Auction auction = auctionRepository.findById(id).orElseThrow(
                () -> new NullPointerException("존재하지 않는 물품입니다.") // null 처리
        );
        if (users.getUsername().equals(auction.getUser().getUsername())){ // 게시글의 아이디와 유저의 아이디 비교
            auction.Up(auction);
        } else
            throw new IllegalArgumentException("권한이 없습니다");
        return ResponseDto.setSuccess("끌어 올려졌습니다!", null);
    }

    @Transactional
    public ResponseDto delAuction(Long id, Users users){
        Auction auction = auctionRepository.findById(id).orElseThrow(
                () -> new NullPointerException("존재하지 않는 물품입니다.") // null 처리
        );
        if (users.getUsername().equals(auction.getUser().getUsername())){ // 게시글의 아이디와 유저의 아이디 비교
            auctionRepository.deleteById(id);
        } else
            throw new IllegalArgumentException("권한이 없습니다");
        return ResponseDto.setSuccess("물품이 삭제되었습니다.", null);
    }
}
