package com.example.getreadyauction.service;

import com.example.getreadyauction.dto.ResponseDto;
import com.example.getreadyauction.dto.auction.*;
import com.example.getreadyauction.entity.Auction;
import com.example.getreadyauction.entity.Bid;
import com.example.getreadyauction.entity.ErrorCode;
import com.example.getreadyauction.entity.Users;
import com.example.getreadyauction.exception.CustomException;
import com.example.getreadyauction.repository.AuctionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class AuctionService {

    private final AuctionRepository auctionRepository;

    @Transactional(readOnly = true)
    public ResponseDto<List<AuctionResponseDto>> getAllAuctions(Pageable pageable) {
        List<Auction> auctionList = auctionRepository.findAllByOrderByCreatedAtDesc(pageable).getContent();
        List<AuctionResponseDto> auctionResponseDtoList = auctionList.stream().map(AuctionResponseDto::new).collect(Collectors.toList());
        return ResponseDto.setSuccess("Success : get All Auctions Information", auctionResponseDtoList);
    }

    @Transactional(readOnly = true)
    public ResponseDto<List<AuctionResponseDto>> getCategorizedAuctions(Pageable pageable, AuctionCategoryRequestDto auctionCategoryRequestDto) {
        List<Auction> auctionList = auctionRepository.findAllByCategoryOrderByModifiedAtDesc(pageable, auctionCategoryRequestDto.getCategory()).getContent();
        List<AuctionResponseDto> auctionResponseDtoList = auctionList.stream().map(AuctionResponseDto::new).collect(Collectors.toList());
        return ResponseDto.setSuccess("Success : get All Categorized Auctions Information", auctionResponseDtoList);
    }

    @Transactional(readOnly = true)
    public ResponseDto<List<AuctionResponseDto>> getSearchedAuction(Pageable pageable, AuctionSearchRequestDto auctionSearchRequestDto) {
        List<Auction> auctionList = auctionRepository.findAllByTitleContaining(pageable, auctionSearchRequestDto.getSearch()).getContent();
        List<AuctionResponseDto> auctionResponseDtoList = auctionList.stream().map(AuctionResponseDto::new).collect(Collectors.toList());
        return ResponseDto.setSuccess("Success : get All Categorized Auctions Information", auctionResponseDtoList);
    }

    @Transactional
    public ResponseDto<AuctionAllResponseDto> getDetailedAuctions(Long id) {
        Auction auction = validateAuction(id);

        auction.setIsDone(LocalDateTime.now());
        auction.setView();

        if(auction.getBidList().isEmpty())
            return ResponseDto.setSuccess("Success : get Detailed Auction Information", new AuctionAllResponseDto(auction, null));

        List<Bid> tmpList = new ArrayList<>(List.copyOf(auction.getBidList()));
        List<Bid> topBidList = tmpList.size() >= 3 ? tmpList.subList(0, 3) : tmpList.subList(0, tmpList.size());

        return ResponseDto.setSuccess("Success : get Detailed Auction Information", new AuctionAllResponseDto(auction, topBidList));
    }

    public ResponseDto postAddAuction(AuctionRequestDto auctionRequestDto, Users users) { // 저장 서비스
        Auction auction = new Auction(auctionRequestDto, users); // Requestdto 받아서 user 정보와 함께 객체 생성
        auctionRepository.saveAndFlush(auction); // 객체를 레포지토리에 저장
        return ResponseDto.setSuccess("물품이 등록되었습니다", null);
    }

    @Transactional
    public ResponseDto putEditAuction(Long id, AuctionRequestDto auctionRequestDto, Users users) { // 수정 서비스
        Auction auction = validateAuction(id); // 중복된 메서드는 공통 메서드 처리
        if (users.getUsername().equals(auction.getUser().getUsername())) { // 물품 등록자의 id와 수정하려는 사람의 id를 가져와서 비교
            auction.edit(auctionRequestDto); // 맞으면 수정해줌
        } else
            throw new CustomException(ErrorCode.INVALID_AUTHORIZATION); // id 다르면 던져줌
        return ResponseDto.setSuccess("물품이 수정되었습니다", null);
    }

    @Transactional
    public ResponseDto putUpAuction(Long id, Users users) { // 끌올
        Auction auction = validateAuction(id); // 중복된 메서드는 공통 메서드 처리
        if (users.getUsername().equals(auction.getUser().getUsername())) { // 물품 등록자의 id와 수정하려는 사람의 id를 가져와서 비교
            auction.up();  // 맞으면 그냥 담아온 값을 고대로 반영
        } else
            throw new CustomException(ErrorCode.INVALID_AUTHORIZATION); // id가 다르면 던져줌
        return ResponseDto.setSuccess("끌어 올려졌습니다!", null);
    }

    @Transactional
    public ResponseDto delAuction(Long id, Users users) { // 물품 삭제
        Auction auction = validateAuction(id); // 중복된 메서드는 공통 메서드 처리
        if (users.getUsername().equals(auction.getUser().getUsername())) { // 물품 등록자의 id와 수정하려는 사람의 id를 가져와서 비교
            auctionRepository.deleteById(id); // 맞으면 받아온 id값을 기반으로 레포지토리에서 삭제
        } else
            throw new CustomException(ErrorCode.INVALID_AUTHORIZATION); // id 다르면 던져줌
        return ResponseDto.setSuccess("물품이 삭제되었습니다.", null);
    }

    public Auction validateAuction(Long id) {
        return auctionRepository.findById(id).orElseThrow(() -> new CustomException(ErrorCode.NOSUCH_AUCTION));
    }
}

