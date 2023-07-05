package com.example.getreadyauction.service;

import com.example.getreadyauction.dto.BidRequestDto;
import com.example.getreadyauction.dto.auction.AuctionAllResponseDto;
import com.example.getreadyauction.dto.auction.AuctionRequestDto;
import com.example.getreadyauction.dto.auction.AuctionResponseDto;
import com.example.getreadyauction.entity.*;
import com.example.getreadyauction.exception.CustomException;
import com.example.getreadyauction.repository.AuctionRepository;
import com.example.getreadyauction.repository.BidRepository;
import com.example.getreadyauction.repository.UsersRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThrows;

@ActiveProfiles("test")
@SpringBootTest
class AuctionServiceTest {

    @Autowired
    private AuctionService auctionService;

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private AuctionRepository auctionRepository;

    @Autowired
    private BidRepository bidRepository;

    private Users testUser1;

    @BeforeEach
    void setUp() {
        testUser1 = new Users("testname1", "@testPassword1");
        usersRepository.save(testUser1);
    }

    @AfterEach
    void tearDown() {
        bidRepository.deleteAllInBatch();
        auctionRepository.deleteAllInBatch();
        usersRepository.deleteAllInBatch();
    }

    @Test
    @DisplayName("모든 경매 물품 조회")
    void getAllAuctions() {
        //given
        AuctionRequestDto testAuctionRequestDto1 = AuctionRequestDto.builder()
                .title("test1의 쇼파").category(CategoryType.FURNITURE).content("쇼파").minPrice(13000).deadline("2000년 01월 01일 00시 00분 00초").build();
        AuctionRequestDto testAuctionRequestDto2 = AuctionRequestDto.builder()
                .title("test1의 셔츠").category(CategoryType.FASSION).content("셔츠").minPrice(5000).deadline("2000년 01월 02일 00시 00분 00초").build();
        AuctionRequestDto testAuctionRequestDto3 = AuctionRequestDto.builder()
                .title("test1의 핸드폰").category(CategoryType.ELECTRONICS).content("핸드폰").minPrice(7000).deadline("2000년 01월 03일 00시 00분 00초").build();

        Auction testAuction1 = Auction.builder().auctionRequestDto(testAuctionRequestDto1).user(testUser1).build();
        Auction testAuction2 = Auction.builder().auctionRequestDto(testAuctionRequestDto2).user(testUser1).build();
        Auction testAuction3 = Auction.builder().auctionRequestDto(testAuctionRequestDto3).user(testUser1).build();

        auctionRepository.saveAll(List.of(testAuction1, testAuction2, testAuction3));
        Pageable pageable = PageRequest.of(0, 5);

        //when
        ResponseEntity<List<AuctionResponseDto>> testResult = auctionService.getAllAuctions(pageable);

        //then
        assertThat(testResult.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(testResult.getBody())
                .hasSize(3)
                .usingRecursiveComparison()
                .isEqualTo(List.of(
                        new AuctionResponseDto(testAuction3),
                        new AuctionResponseDto(testAuction2),
                        new AuctionResponseDto(testAuction1)
                ));
    }

    @Test
    @DisplayName("특정 카테고리의 모든 경매 물품 조회")
    void getCategorizedAuctions() {
        //given
        AuctionRequestDto testAuctionRequestDto1 = AuctionRequestDto.builder()
                .title("test1의 쇼파").category(CategoryType.FURNITURE).content("쇼파").minPrice(13000).deadline("2000년 01월 01일 00시 00분 00초").build();
        AuctionRequestDto testAuctionRequestDto2 = AuctionRequestDto.builder()
                .title("test1의 침대").category(CategoryType.FURNITURE).content("침대").minPrice(20000).deadline("2000년 01월 02일 00시 00분 00초").build();
        AuctionRequestDto testAuctionRequestDto3 = AuctionRequestDto.builder()
                .title("test1의 셔츠").category(CategoryType.FASSION).content("셔츠").minPrice(5000).deadline("2000년 01월 03일 00시 00분 00초").build();

        Auction testAuction1 = Auction.builder().auctionRequestDto(testAuctionRequestDto1).user(testUser1).build();
        Auction testAuction2 = Auction.builder().auctionRequestDto(testAuctionRequestDto2).user(testUser1).build();
        Auction testAuction3 = Auction.builder().auctionRequestDto(testAuctionRequestDto3).user(testUser1).build();

        auctionRepository.saveAll(List.of(testAuction1, testAuction2, testAuction3));
        Pageable pageable = PageRequest.of(0, 5);

        //when
        ResponseEntity<List<AuctionResponseDto>> testResult = auctionService.getCategorizedAuctions(pageable, CategoryType.FURNITURE);

        //then
        assertThat(testResult.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(testResult.getBody())
                .hasSize(2)
                .usingRecursiveComparison()
                .isEqualTo(List.of(
                        new AuctionResponseDto(testAuction2),
                        new AuctionResponseDto(testAuction1)
                ));
    }

    @Test
    @DisplayName("제목에 특정 검색어가 포함된 모든 경매 물품 조회")
    void getSearchedAuction() {
        //given
        AuctionRequestDto testAuctionRequestDto1 = AuctionRequestDto.builder()
                .title("test1의 쇼파").category(CategoryType.FURNITURE).content("쇼파").minPrice(13000).deadline("2000년 01월 01일 00시 00분 00초").build();
        AuctionRequestDto testAuctionRequestDto2 = AuctionRequestDto.builder()
                .title("침대").category(CategoryType.FURNITURE).content("침대").minPrice(20000).deadline("2000년 01월 02일 00시 00분 00초").build();
        AuctionRequestDto testAuctionRequestDto3 = AuctionRequestDto.builder()
                .title("test1의 셔츠").category(CategoryType.FASSION).content("셔츠").minPrice(5000).deadline("2000년 01월 03일 00시 00분 00초").build();

        Auction testAuction1 = Auction.builder().auctionRequestDto(testAuctionRequestDto1).user(testUser1).build();
        Auction testAuction2 = Auction.builder().auctionRequestDto(testAuctionRequestDto2).user(testUser1).build();
        Auction testAuction3 = Auction.builder().auctionRequestDto(testAuctionRequestDto3).user(testUser1).build();

        auctionRepository.saveAll(List.of(testAuction1, testAuction2, testAuction3));

        Pageable pageable = PageRequest.of(0, 5);
        String search = "test1";

        //when
        ResponseEntity<List<AuctionResponseDto>> testResult = auctionService.getSearchedAuction(pageable, search);

        //then
        assertThat(testResult.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(testResult.getBody())
                .usingRecursiveComparison()
                .isEqualTo(List.of(
                        new AuctionResponseDto(testAuction3),
                        new AuctionResponseDto(testAuction1)
                ));
    }

    @Test
    @Transactional
    @DisplayName("특정 경매 물품의 상세 내역 조회")
    void getDetailedAuctions() {
        //given
        AuctionRequestDto testAuctionRequestDto1 = AuctionRequestDto.builder()
                .title("test1의 쇼파").category(CategoryType.FURNITURE).content("쇼파").minPrice(13000).deadline("2000년 01월 01일 00시 00분 00초").build();

        Auction testAuction1 = Auction.builder().auctionRequestDto(testAuctionRequestDto1).user(testUser1).build();
        auctionRepository.save(testAuction1);

        Users testUser2 = new Users("testname2", "@testPassword2");
        Users testUser3 = new Users("testname3", "@testPassword3");
        usersRepository.saveAll(List.of(testUser2, testUser3));

        BidRequestDto testBidRequestDto2 = new BidRequestDto(15000);
        Bid testBid2 = new Bid(testBidRequestDto2, testUser2, testAuction1);
        BidRequestDto testBidRequestDto3 = new BidRequestDto(16000);
        Bid testBid3 = new Bid(testBidRequestDto3, testUser3, testAuction1);

        bidRepository.saveAll(List.of(testBid2, testBid3));

        List<Bid> expectedResult = new ArrayList<>(List.of(testBid2, testBid3));
        expectedResult.sort(Comparator.comparing(Bid::getModifiedAt).reversed());

        //when
        ResponseEntity<AuctionAllResponseDto> testResult = auctionService.getDetailedAuctions(testAuction1.getId());

        //then
        assertThat(testResult.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(testResult.getBody())
                .usingRecursiveComparison()
                .isEqualTo(new AuctionAllResponseDto(testAuction1, expectedResult));
    }

    @Test
    @DisplayName("경매 물품 등록")
    void addAuction() {
        //given
        AuctionRequestDto testAuctionRequestDto = AuctionRequestDto.builder()
                .title("test1의 쇼파").category(CategoryType.FURNITURE).content("쇼파").minPrice(13000).deadline("2000년 01월 01일 00시 00분 00초").build();

        //when
        ResponseEntity<String> testResult = auctionService.addAuction(testAuctionRequestDto, testUser1);

        //then
        assertThat(testResult.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(testResult.getBody()).isEqualTo("경매 물품 등록 완료");
    }

    @Test
    @DisplayName("경매 물품 수정")
    void editAuction() {
        //given
        AuctionRequestDto testAuctionRequestDto = AuctionRequestDto.builder()
                .title("test1의 쇼파").category(CategoryType.FURNITURE).content("쇼파").minPrice(13000).deadline("2000년 01월 01일 00시 00분 00초").build();

        Auction testAuction = Auction.builder().auctionRequestDto(testAuctionRequestDto).user(testUser1).build();
        auctionRepository.save(testAuction);

        AuctionRequestDto testAuctionModifyDto = AuctionRequestDto.builder()
                .title("test1의 쇼파").category(CategoryType.FURNITURE).content("책꽂이").minPrice(13000).deadline("2000년 01월 01일 00시 00분 00초").build();

        //when
        ResponseEntity<String> testResult = auctionService.editAuction(testAuction.getId(), testAuctionModifyDto, "testname1");

        //then
        assertThat(testResult.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(testResult.getBody()).isEqualTo("경매 물품 수정 완료");
    }

    @Test
    @DisplayName("경매 물품 수정 : 본인이 올린 경매 물품이 아닐 때")
    void editAuctionWithNonAuthorization() {
        //given
        AuctionRequestDto testAuctionRequestDto = AuctionRequestDto.builder()
                .title("test1의 쇼파").category(CategoryType.FURNITURE).content("쇼파").minPrice(13000).deadline("2000년 01월 01일 00시 00분 00초").build();

        Auction testAuction = Auction.builder().auctionRequestDto(testAuctionRequestDto).user(testUser1).build();
        auctionRepository.save(testAuction);

        AuctionRequestDto testAuctionModifyDto = AuctionRequestDto.builder()
                .title("test1의 쇼파").category(CategoryType.FURNITURE).content("책꽂이").minPrice(13000).deadline("2000년 01월 01일 00시 00분 00초").build();

        //when & then
        CustomException testResult = assertThrows(CustomException.class,
                () -> auctionService.editAuction(testAuction.getId(), testAuctionModifyDto, "testname2"));
        assertThat(testResult.getErrorCode()).isEqualTo(ErrorCode.INVALID_AUTHORIZATION);
    }

    @Test
    @DisplayName("경매 물품 삭제")
    void deleteAuction() {
        //given
        AuctionRequestDto testAuctionRequestDto = AuctionRequestDto.builder()
                .title("test1의 쇼파").category(CategoryType.FURNITURE).content("쇼파").minPrice(13000).deadline("2000년 01월 01일 00시 00분 00초").build();

        Auction testAuction = Auction.builder().auctionRequestDto(testAuctionRequestDto).user(testUser1).build();
        auctionRepository.save(testAuction);

        //when
        ResponseEntity<String> testResult = auctionService.deleteAuction(testAuction.getId(), "testname1");

        //then
        assertThat(testResult.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(testResult.getBody()).isEqualTo("경매 물품 삭제 완료");
    }

    @Test
    @DisplayName("경매 물품 삭제 : 본인이 올린 경매 물품이 아닐 때")
    void deleteAuctionWithNonAuthorization() {
        //given
        AuctionRequestDto testAuctionRequestDto = AuctionRequestDto.builder()
                .title("test1의 쇼파").category(CategoryType.FURNITURE).content("쇼파").minPrice(13000).deadline("2000년 01월 01일 00시 00분 00초").build();

        Auction testAuction = Auction.builder().auctionRequestDto(testAuctionRequestDto).user(testUser1).build();
        auctionRepository.save(testAuction);

        //when & then
        CustomException testResult = assertThrows(CustomException.class,
                () -> auctionService.deleteAuction(testAuction.getId(), "testname2"));
        assertThat(testResult.getErrorCode()).isEqualTo(ErrorCode.INVALID_AUTHORIZATION);
    }
}