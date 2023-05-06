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

    public ResponseDto postAddAuction(AuctionRequestDto auctionRequestDto, Users users){ // 저장 서비스
        Auction auction = new Auction(auctionRequestDto, users); // Requestdto 받아서 user 정보와 함께 객체 생성
        auctionRepository.saveAndFlush(auction); // 객체를 레포지토리에 저장
        return ResponseDto.setSuccess("물품이 등록되었습니다", null);
    }

    @Transactional
    public ResponseDto putEditAuction(Long id, AuctionRequestDto auctionRequestDto, Users users){ // 수정 서비스
        Auction auction = auctionRepository.findById(id).orElseThrow(
                () -> new NullPointerException("존재하지 않는 물품입니다")  // id값을 받아서 존재하는 물품인지 찾아서 담아줌, 혹시 없으면 null 처리
        );
        if (users.getUsername().equals(auction.getUser().getUsername())){ // 물품 등록자의 id와 수정하려는 사람의 id를 가져와서 비교
            auction.Edit(auctionRequestDto); // 맞으면 수정해줌
        } else
            throw new IllegalArgumentException("권한이 없습니다"); // id 다르면 던져줌
        return ResponseDto.setSuccess("물품이 수정되었습니다", null);
    }
    @Transactional
    public ResponseDto putUpAuction(Long id, Users users){ // 끌올
        Auction auction = auctionRepository.findById(id).orElseThrow(
                () -> new NullPointerException("존재하지 않는 물품입니다.") // id값을 받아서 존재하는 물품인지 찾아서 담아줌, 혹시 없으면 null 처리
        );
        if (users.getUsername().equals(auction.getUser().getUsername())){ // 물품 등록자의 id와 수정하려는 사람의 id를 가져와서 비교
            auction.Up(auction); // 맞으면 그냥 담아온 값을 고대로 반영
        } else
            throw new IllegalArgumentException("권한이 없습니다"); // id가 다르면 던져줌
        return ResponseDto.setSuccess("끌어 올려졌습니다!", null);
    }

    @Transactional
    public ResponseDto delAuction(Long id, Users users){ // 물품 삭제
        Auction auction = auctionRepository.findById(id).orElseThrow(
                () -> new NullPointerException("존재하지 않는 물품입니다.") // id값을 받아서 존재하는 물품인지 찾아서 담아줌, 혹시 없으면 null 처리
        );
        if (users.getUsername().equals(auction.getUser().getUsername())){ // 물품 등록자의 id와 수정하려는 사람의 id를 가져와서 비교
            auctionRepository.deleteById(id); // 맞으면 받아온 id값을 기반으로 레포지토리에서 삭제
        } else
            throw new IllegalArgumentException("권한이 없습니다"); // id 다르면 던져줌
        return ResponseDto.setSuccess("물품이 삭제되었습니다.", null);
    }
}
