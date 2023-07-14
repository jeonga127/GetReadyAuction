package com.example.getreadyauction.domain.mypage.controller;

import com.example.getreadyauction.domain.auction.dto.AuctionRequestDto;
import com.example.getreadyauction.domain.auction.dto.AuctionResponseDto;
import com.example.getreadyauction.domain.auction.entity.Auction;
import com.example.getreadyauction.domain.auction.entity.CategoryType;
import com.example.getreadyauction.domain.scheduler.service.SchedulerService;
import com.example.getreadyauction.domain.user.entity.Users;
import com.example.getreadyauction.security.UserDetailsImpl;
import com.example.getreadyauction.domain.mypage.service.MyPageService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@MockBean(SchedulerService.class)
@ExtendWith(SpringExtension.class)
class MypageControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MyPageService myPageService;

    private UserDetailsImpl userDetails;
    private Auction testAuction;
    private ResponseEntity<List<AuctionResponseDto>> testResult;

    @BeforeEach
    void setUp() {
        Users testUser1 = new Users("testname1", "@testPassword1");
        userDetails = new UserDetailsImpl(testUser1, testUser1.getUsername());

        AuctionRequestDto testAuctionRequestDto = AuctionRequestDto.builder()
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

        testResult = ResponseEntity.ok()
                .body(List.of(new AuctionResponseDto(testAuction)));
    }

    @Test
    @DisplayName("사용자의 모든 경매 물품 조회 GET 요청")
    void getAllMyAuctions() throws Exception {
        //given
        when(myPageService.getAllMyAuctions(Mockito.any(Pageable.class), Mockito.any(Users.class))).thenReturn(testResult);

        //when & then
        mockMvc.perform(get("/mypage/auction")
                        .param("page", "0")
                        .param("size", "5")
                        .with(user(userDetails)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(testAuction.getId()))
                .andExpect(jsonPath("$[0].title").value(testAuction.getTitle()))
                .andExpect(jsonPath("$[0].category").value(testAuction.getCategory().getText()))
                .andExpect(jsonPath("$[0].currentPrice").value(testAuction.getCurrentPrice()))
                .andExpect(jsonPath("$[0].deadline").value(testAuction.getDeadline().toString()));
    }

    @Test
    @DisplayName("사용자의 모든 입찰 내역 조회 GET 요청")
    void getAllMyBids() throws Exception {
        //given
        when(myPageService.getAllMyBids(Mockito.any(Pageable.class), Mockito.any(Users.class))).thenReturn(testResult);

        //when & then
        mockMvc.perform(get("/mypage/bid")
                        .param("page", "0")
                        .param("size", "5")
                        .with(user(userDetails)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(testAuction.getId()))
                .andExpect(jsonPath("$[0].title").value(testAuction.getTitle()))
                .andExpect(jsonPath("$[0].category").value(testAuction.getCategory().getText()))
                .andExpect(jsonPath("$[0].currentPrice").value(testAuction.getCurrentPrice()))
                .andExpect(jsonPath("$[0].deadline").value(testAuction.getDeadline().toString()));
    }
}