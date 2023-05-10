package com.example.getreadyauction.controller;

import com.example.getreadyauction.dto.ResponseDto;
import com.example.getreadyauction.dto.auction.*;
import com.example.getreadyauction.security.UserDetailsImpl;
import com.example.getreadyauction.service.AuctionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("auction")
public class AuctionController {

    private final AuctionService auctionService;

    @GetMapping//경매 전체 조회
    public ResponseDto<List<AuctionResponseDto>> getAllAuctions(Pageable pageable){
        return auctionService.getAllAuctions(pageable);
    }//Pageable는 Spring Data 라이브러리에서 제공하는 인터페이스로, 페이징 및 정렬 정보를 담고 있음/
    //Pageable 인스턴스는 일반적으로 컨트롤러 메소드의 매개변수로 사용되며, Spring MVC가 클라이언트의 요청에서 적절한 정보를 추출하여 Pageable 객체를 자동으로 생성하고 이를 메소드에 주입
//클라이언트가 요청을 보낼 때 page=3&size=10&sort=id,desc와 같은 파라미터를 URL에 포함시킬 수 있음. 여기서 page는 페이지 번호, size는 페이지당 항목 수, sort는 정렬 방식을 나타냄
    @GetMapping("/category")//경매 카테고리 조회
    public ResponseDto<List<AuctionResponseDto>> getCategorizedAuctions(Pageable pageable, @RequestParam("category") String category){
        return auctionService.getCategorizedAuctions(pageable, category);
    }

    @GetMapping("/search")//경매 검색
    public ResponseDto<List<AuctionResponseDto>> getSearchedAuctions(Pageable pageable, @RequestParam("search") String search) {
        return auctionService.getSearchedAuction(pageable, search);
    }

    @GetMapping("/{id}")//경매 상세 조회
    public ResponseDto<AuctionAllResponseDto> getDetailedAuctions(@PathVariable Long id){
        return auctionService.getDetailedAuctions(id);
    }

    @PostMapping("/add")//경매 등록
    public ResponseDto postAddAuction(@RequestBody AuctionRequestDto auctionAddRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return auctionService.postAddAuction(auctionAddRequestDto, userDetails.getUser());
    }

    @PutMapping("/edit/{editId}") //경매 수정
    public ResponseDto putEditAuction(@PathVariable("editId") Long editId, @RequestBody AuctionRequestDto auctionAddRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return auctionService.putEditAuction(editId, auctionAddRequestDto, userDetails.getUser());
    }

    @PutMapping("/up/{upId}") //경매 끌올
    public ResponseDto putUpAuction(@PathVariable("upId") Long upId, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return auctionService.putUpAuction(upId, userDetails.getUser());
    }

    @DeleteMapping("/delete/{deleteId}")//경매 삭제
    public ResponseDto delAuction(@PathVariable("deleteId") Long deleteId, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return auctionService.delAuction(deleteId, userDetails.getUser());
    }
}
