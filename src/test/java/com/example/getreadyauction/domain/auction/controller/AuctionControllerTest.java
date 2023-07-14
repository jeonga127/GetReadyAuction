package com.example.getreadyauction.domain.auction.controller;

import com.example.getreadyauction.domain.bid.dto.BidRequestDto;
import com.example.getreadyauction.domain.auction.dto.AuctionAllResponseDto;
import com.example.getreadyauction.domain.auction.dto.AuctionRequestDto;
import com.example.getreadyauction.domain.auction.dto.AuctionResponseDto;
import com.example.getreadyauction.domain.auction.entity.Auction;
import com.example.getreadyauction.domain.bid.entity.Bid;
import com.example.getreadyauction.domain.auction.entity.CategoryType;
import com.example.getreadyauction.domain.scheduler.service.SchedulerService;
import com.example.getreadyauction.domain.user.entity.Users;
import com.example.getreadyauction.security.UserDetailsImpl;
import com.example.getreadyauction.domain.auction.service.AuctionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@MockBean(SchedulerService.class)
@ExtendWith(SpringExtension.class)
class AuctionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private AuctionService auctionService;

    private Users testUser1;
    private AuctionRequestDto testAuctionRequestDto;
    private Auction testAuction;

    @BeforeEach
    void setUp() {
        testUser1 = new Users("testname1", "@testPassword1");

        testAuctionRequestDto = AuctionRequestDto.builder()
                .title("test1의 쇼파")
                .category(CategoryType.FURNITURE)
                .content("쇼파")
                .minPrice(13000)
                .deadline(LocalDateTime.parse("2000-01-01T00:00:01"))
                .build();

        testAuction = Auction.builder()
                .auctionRequestDto(testAuctionRequestDto)
                .user(testUser1)
                .build();
    }

    @Test
    @DisplayName("모든 경매 물품 조회 GET 요청")
    void getAllAuctions() throws Exception {
        //given
        ResponseEntity<List<AuctionResponseDto>> testResult = ResponseEntity.ok()
                .body(List.of(new AuctionResponseDto(testAuction)));

        Pageable pageable = PageRequest.of(0, 5);
        when(auctionService.getAllAuctions(pageable)).thenReturn(testResult);

        //when & then
        mockMvc.perform(get("/auction")
                        .param("page", "0")
                        .param("size", "5")
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(testAuction.getId()))
                .andExpect(jsonPath("$[0].title").value(testAuction.getTitle()))
                .andExpect(jsonPath("$[0].category").value(testAuction.getCategory().getText()))
                .andExpect(jsonPath("$[0].currentPrice").value(testAuction.getCurrentPrice()))
                .andExpect(jsonPath("$[0].deadline").value(testAuction.getDeadline().toString()));
    }

    @Test
    @DisplayName("특정 카테고리의 경매 물품 조회 GET 요청")
    void getCategorizedAuctions() throws Exception {
        //given
        ResponseEntity<List<AuctionResponseDto>> testResult = ResponseEntity.ok()
                .body(List.of(new AuctionResponseDto(testAuction)));

        Pageable pageable = PageRequest.of(0, 5);
        when(auctionService.getCategorizedAuctions(pageable, CategoryType.FURNITURE)).thenReturn(testResult);

        //when & then
        mockMvc.perform(get("/auction/category")
                        .param("page", "0")
                        .param("size", "5")
                        .param("category", "FURNITURE")
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(testAuction.getId()))
                .andExpect(jsonPath("$[0].title").value(testAuction.getTitle()))
                .andExpect(jsonPath("$[0].category").value(testAuction.getCategory().getText()))
                .andExpect(jsonPath("$[0].currentPrice").value(testAuction.getCurrentPrice()))
                .andExpect(jsonPath("$[0].deadline").value(testAuction.getDeadline().toString()));
    }

    @Test
    @DisplayName("특정 검색어가 포함된 모든 경매 물품 조회 GET 요청")
    void getSearchedAuctions() throws Exception {
        //given
        ResponseEntity<List<AuctionResponseDto>> testResult = ResponseEntity.ok()
                .body(List.of(new AuctionResponseDto(testAuction)));

        Pageable pageable = PageRequest.of(0, 5);
        when(auctionService.getSearchedAuction(pageable, "쇼파")).thenReturn(testResult);

        //when & then
        mockMvc.perform(get("/auction/search")
                        .param("page", "0")
                        .param("size", "5")
                        .param("search", "쇼파")
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(testAuction.getId()))
                .andExpect(jsonPath("$[0].title").value(testAuction.getTitle()))
                .andExpect(jsonPath("$[0].category").value(testAuction.getCategory().getText()))
                .andExpect(jsonPath("$[0].currentPrice").value(testAuction.getCurrentPrice()))
                .andExpect(jsonPath("$[0].deadline").value(testAuction.getDeadline().toString()));
    }

    @Test
    @DisplayName("특정 경매 물품의 상세 정보 조회 GET 요청")
    void getDetailedAuctions() throws Exception {
        //given
        Users testUser2 = new Users("testname2", "@testPassword2");
        BidRequestDto testBidRequestDto2 = new BidRequestDto(15000);
        Bid testBid = new Bid(testBidRequestDto2, testUser2, testAuction);
        List<Bid> bidList = new ArrayList<>(List.of(testBid));

        ResponseEntity<AuctionAllResponseDto> testResult = ResponseEntity.ok()
                .body(new AuctionAllResponseDto(testAuction, bidList));

        when(auctionService.getDetailedAuctions(Mockito.anyLong())).thenReturn(testResult);

        //when & then
        mockMvc.perform(get("/auction/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(testAuction.getId()))
                .andExpect(jsonPath("$.title").value(testAuction.getTitle()))
                .andExpect(jsonPath("$.category").value(testAuction.getCategory().getText()))
                .andExpect(jsonPath("$.username").value(testAuction.getUser().getUsername()))
                .andExpect(jsonPath("$.content").value(testAuction.getContent()))
                .andExpect(jsonPath("$.successBid").value(testAuction.getSuccessBid()))
                .andExpect(jsonPath("$.deadline").value(testAuction.getDeadline().toString()))
                .andExpect(jsonPath("$.modifiedAt").value(testAuction.getModifiedAt()))
                .andExpect(jsonPath("$.bidList[0].user.username").value(testBid.getUser().getUsername()))
                .andExpect(jsonPath("$.bidList[0].price").value(testBid.getPrice()))
                .andExpect(jsonPath("$.minPrice").value(testAuction.getMinPrice()))
                .andExpect(jsonPath("$.currentPrice").value(testAuction.getCurrentPrice()))
                .andExpect(jsonPath("$.views").value(testAuction.getViews()))
                .andExpect(jsonPath("$.done").value(testAuction.isDone()));
    }

    @Test
    @DisplayName("경매 물품 등록 POST 요청 : 인증된 사용자")
    void addAuction() throws Exception {
        //given
        UserDetailsImpl userDetails = new UserDetailsImpl(testUser1, testUser1.getUsername());

        ResponseEntity<String> testResult = ResponseEntity.ok("경매 물품 등록 완료");
        when(auctionService.addAuction(Mockito.any(AuctionRequestDto.class), Mockito.any(Users.class))).thenReturn(testResult);

        //when & then
        mockMvc.perform(post("/auction/add")
                        .content(objectMapper.writeValueAsString(testAuctionRequestDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(user(userDetails)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("경매 물품 등록 완료"));
    }

    @Test
    @DisplayName("경매 물품 등록 POST 요청 : 인증되지 않은 사용자")
    void addAuctionWithNoAuthorization() throws Exception {
        //given & when & then
        mockMvc.perform(post("/auction/add")
                        .content(objectMapper.writeValueAsString(testAuctionRequestDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("경매 물품 수정 PUT 요청 : 인증된 사용자")
    void editAuction() throws Exception{
        //given
        UserDetailsImpl userDetails = new UserDetailsImpl(testUser1, testUser1.getUsername());

        ResponseEntity<String> testResult = ResponseEntity.ok("경매 물품 수정 완료");
        when(auctionService.editAuction(Mockito.anyLong(), Mockito.any(AuctionRequestDto.class), Mockito.anyString())).thenReturn(testResult);

        //when & then
        mockMvc.perform(put("/auction/edit/{editId}", 1L)
                        .content(objectMapper.writeValueAsString(testAuctionRequestDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(user(userDetails)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("경매 물품 수정 완료"));
    }

    @Test
    @DisplayName("경매 물품 수정 PUT 요청 : 인증된 사용자")
    void editAuctionWithNoAuthorization() throws Exception{
        //given & when & then
        mockMvc.perform(put("/auction/edit/{editId}", 1L)
                        .content(objectMapper.writeValueAsString(testAuctionRequestDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isForbidden());
    }


    @Test
    @DisplayName("경매 물품 삭제 DELETE 요청 : 인증된 사용자")
    void deleteAuction() throws Exception{
        //given
        UserDetailsImpl userDetails = new UserDetailsImpl(testUser1, testUser1.getUsername());

        ResponseEntity<String> testResult = ResponseEntity.ok("경매 물품 삭제 완료");
        when(auctionService.deleteAuction(Mockito.anyLong(), Mockito.anyString())).thenReturn(testResult);

        //when & then
        mockMvc.perform(delete("/auction/delete/{deleteId}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(user(userDetails)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("경매 물품 삭제 완료"));
    }

    @Test
    @DisplayName("경매 물품 삭제 DELETE 요청 : 인증되지 않은 사용자")
    void deleteAuctionWithNoAuthorization() throws Exception {
        //given & when & then
        mockMvc.perform(delete("/auction/delete/{deleteId}", 1L)
                        .content(objectMapper.writeValueAsString(testAuctionRequestDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isForbidden());
    }
}