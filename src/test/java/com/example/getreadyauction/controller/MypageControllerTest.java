package com.example.getreadyauction.controller;

import com.example.getreadyauction.service.MyPageService;
import com.example.getreadyauction.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
class MypageControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private MyPageService myPageService;

    @Test
    @DisplayName("사용자의 모든 경매 물품 조회 GET 요청")
    void myAuctions() throws Exception {
    }

    @Test
    @DisplayName("사용자의 모든 입찰 내역 조회 GET 요청")
    void myBid() throws Exception {
    }
}