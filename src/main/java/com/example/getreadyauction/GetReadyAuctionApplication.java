package com.example.getreadyauction;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@EnableJpaAuditing
@SpringBootApplication
public class GetReadyAuctionApplication {

    public static void main(String[] args) {
        SpringApplication.run(GetReadyAuctionApplication.class, args);
    }

}
