package com.example.getreadyauction.repository;

import com.example.getreadyauction.dto.BidRequestDto;
import com.example.getreadyauction.dto.auction.AuctionRequestDto;
import com.example.getreadyauction.entity.Auction;
import com.example.getreadyauction.entity.Bid;
import com.example.getreadyauction.entity.CategoryType;
import com.example.getreadyauction.entity.Users;
import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@DataJpaTest
class AuctionRepositoryTest {

    @Autowired
    private AuctionRepository auctionRepository;

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private BidRepository bidRepository;

    private Users testUser1;
    private Users testUser2;

    private Auction testAuction1;
    private Auction testAuction2;
    private Auction testAuction3;
    private Auction testAuction4;
    private Auction testAuction5;
    private Auction testAuction6;
    private Auction testAuction7;
    private Auction testAuction8;
    private Auction testAuction9;
    private Auction testAuction10;

    @BeforeEach
    void setup() {
        testUser1 = new Users("testName1", "testPW1");
        testUser2 = new Users("testName2", "testPW2");
        usersRepository.saveAll(List.of(testUser1, testUser2));

        AuctionRequestDto testAuctionRequestDto1 = AuctionRequestDto.builder()
                .title("test1의 쇼파").category(CategoryType.FURNITURE).content("쇼파").minPrice(13000).deadline("2000년 01월 01일 00시 00분 00초").build();
        AuctionRequestDto testAuctionRequestDto2 = AuctionRequestDto.builder()
                .title("test2의 침대").category(CategoryType.FURNITURE).content("침대").minPrice(20000).deadline("2000년 01월 02일 00시 00분 00초").build();
        AuctionRequestDto testAuctionRequestDto3 = AuctionRequestDto.builder()
                .title("test1의 셔츠").category(CategoryType.FASSION).content("셔츠").minPrice(5000).deadline("2000년 01월 03일 00시 00분 00초").build();
        AuctionRequestDto testAuctionRequestDto4 = AuctionRequestDto.builder()
                .title("test2의 바지").category(CategoryType.FASSION).content("바지").minPrice(25000).deadline("2000년 01월 04일 00시 00분 00초").build();
        AuctionRequestDto testAuctionRequestDto5 = AuctionRequestDto.builder()
                .title("test1의 핸드폰").category(CategoryType.ELECTRONICS).content("핸드폰").minPrice(7000).deadline("2000년 01월 05일 00시 00분 00초").build();
        AuctionRequestDto testAuctionRequestDto6 = AuctionRequestDto.builder()
                .title("test2의 모니터").category(CategoryType.ELECTRONICS).content("모니터").minPrice(50000).deadline("2000년 01월 06일 00시 00분 00초").build();
        AuctionRequestDto testAuctionRequestDto7 = AuctionRequestDto.builder()
                .title("test1의 농구공").category(CategoryType.SPORTS).content("농구공").minPrice(100).deadline("2000년 01월 07일 00시 00분 00초").build();
        AuctionRequestDto testAuctionRequestDto8 = AuctionRequestDto.builder()
                .title("test2의 축구화").category(CategoryType.SPORTS).content("축구화").minPrice(120000).deadline("2000년 01월 08일 00시 00분 00초").build();
        AuctionRequestDto testAuctionRequestDto9 = AuctionRequestDto.builder()
                .title("test1의 핸드크림").category(CategoryType.ETC).content("핸드크림").minPrice(1000).deadline("2000년 01월 09일 00시 00분 00초").build();
        AuctionRequestDto testAuctionRequestDto10 = AuctionRequestDto.builder()
                .title("test2의 비타민").category(CategoryType.ETC).content("비타민").minPrice(13500).deadline("2000년 01월 10일 00시 00분 00초").build();

        testAuction1 = Auction.builder().auctionRequestDto(testAuctionRequestDto1).user(testUser1).build();
        testAuction2 = Auction.builder().auctionRequestDto(testAuctionRequestDto2).user(testUser2).build();

        testAuction3 = Auction.builder().auctionRequestDto(testAuctionRequestDto3).user(testUser1).build();
        testAuction4 = Auction.builder().auctionRequestDto(testAuctionRequestDto4).user(testUser2).build();

        testAuction5 = Auction.builder().auctionRequestDto(testAuctionRequestDto5).user(testUser1).build();
        testAuction6 = Auction.builder().auctionRequestDto(testAuctionRequestDto6).user(testUser2).build();

        testAuction7 = Auction.builder().auctionRequestDto(testAuctionRequestDto7).user(testUser1).build();
        testAuction8 = Auction.builder().auctionRequestDto(testAuctionRequestDto8).user(testUser2).build();

        testAuction9 = Auction.builder().auctionRequestDto(testAuctionRequestDto9).user(testUser1).build();
        testAuction10 = Auction.builder().auctionRequestDto(testAuctionRequestDto10).user(testUser2).build();

        auctionRepository.saveAll(List.of(testAuction1, testAuction2,
                testAuction3, testAuction4, testAuction5, testAuction6,
                testAuction7, testAuction8, testAuction9, testAuction10));

        BidRequestDto bidRequest1 = new BidRequestDto(25000);
        Bid bidForList1 = new Bid(bidRequest1, testUser1, testAuction2);

        BidRequestDto bidRequest2 = new BidRequestDto(27000);
        Bid bidForList2 = new Bid(bidRequest2, testUser1, testAuction4);

        BidRequestDto bidRequest3 = new BidRequestDto(53000);
        Bid bidForList3 = new Bid(bidRequest3, testUser1, testAuction6);

        BidRequestDto bidRequest4 = new BidRequestDto(130000);
        Bid bidForList4 = new Bid(bidRequest4, testUser1, testAuction8);

        BidRequestDto bidRequest5 = new BidRequestDto(20000);
        Bid bidForList5 = new Bid(bidRequest5, testUser1, testAuction10);

        bidRepository.saveAll(List.of(bidForList1, bidForList2, bidForList3, bidForList4, bidForList5));
    }

    @AfterEach
    void tearDown() {
        bidRepository.deleteAllInBatch();
        auctionRepository.deleteAllInBatch();
        usersRepository.deleteAllInBatch();
    }

    @Test
    @DisplayName("모든 경매 물품 조회 & 생성일자 기준 내림차순 정렬")
    void findAllByOrderByCreatedAtDesc() {
        //when
        Pageable pageable = PageRequest.of(0, 5);
        List<Auction> auctionList = new ArrayList<>(List.of(testAuction1,
                testAuction2, testAuction3, testAuction4,
                testAuction5, testAuction6, testAuction7,
                testAuction8, testAuction9, testAuction10));
        auctionList.sort(Comparator.comparing(Auction::getCreatedAt).reversed());
        List<Auction> expectedResult = auctionList.subList(0, 5);

        //then
        Page<Auction> testResult = auctionRepository.findAllByOrderByCreatedAtDesc(pageable);
        List<Auction> testResultList = testResult.getContent();

        assertThat(testResultList)
                .hasSize(5)
                .usingRecursiveComparison()
                .isEqualTo(expectedResult);
    }

    @Test
    @DisplayName("특정 카테고리의 모든 경매 물품 조회 & 수정시간 기준 내림차순 정렬")
    void findAllByCategoryOrderByModifiedAtDesc() {
        //when
        Pageable pageable = PageRequest.of(0, 5);
        List<Auction> expectedResult = new ArrayList<>(List.of(testAuction1, testAuction2));
        expectedResult.sort(Comparator.comparing(Auction::getModifiedAt).reversed());

        //then
        Page<Auction> testResult = auctionRepository.findAllByCategoryOrderByModifiedAtDesc(pageable, CategoryType.FURNITURE);
        List<Auction> testResultList = testResult.getContent();
        assertThat(testResultList)
                .hasSize(2)
                .usingRecursiveComparison()
                .isEqualTo(expectedResult);
    }

    @Test
    @DisplayName("제목이 특정 문구를 포함하는 모든 경매 물품 조회 & 수정시간 기준 내림차순 정렬")
    void findAllByTitleContainingByModifiedAtDesc() {
        //when
        Pageable pageable = PageRequest.of(0, 5);
        List<Auction> expectedResult = new ArrayList<>(List.of(testAuction1, testAuction3, testAuction5, testAuction7, testAuction9));
        expectedResult.sort(Comparator.comparing(Auction::getModifiedAt).reversed());

        //then
        Page<Auction> testResult = auctionRepository.findAllByTitleContainingOrderByModifiedAtDesc(pageable, "test1");
        List<Auction> testResultList = testResult.getContent();
        assertThat(testResultList)
                .hasSize(5)
                .usingRecursiveComparison()
                .isEqualTo(expectedResult);
    }

    @Test
    @DisplayName("특정 사용자의 모든 경매 물품 조회 & 수정 시간 내림차순")
    void findAllByUser() {
        //when
        Pageable pageable = PageRequest.of(0, 5);
        List<Auction> expectedResult = new ArrayList<>(List.of(testAuction1, testAuction3, testAuction5, testAuction7, testAuction9));
        expectedResult.sort(Comparator.comparing(Auction::getModifiedAt).reversed());

        //then
        Page<Auction> testResult = auctionRepository.findAllByUserOrderByModifiedAtDesc(pageable, testUser1);
        List<Auction> testResultList = testResult.getContent();

        assertThat(testResultList)
                .hasSize(5)
                .usingRecursiveComparison()
                .isEqualTo(expectedResult);
    }
}