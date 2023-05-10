package com.example.getreadyauction.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration//이 어노테이션은 이 클래스가 Spring IoC 컨테이너에 의해 빈(Bean) 정의를 제공하는 구성 클래스임을 나타냄
public class WebConfig implements WebMvcConfigurer {
    @Override//addCorsMappings(CorsRegistry registry): 이 메소드는 CORS 경로를 설정 /**는 모든 경로에 대해 CORS 설정을 적용하겠다는 것을 의미
    public void addCorsMappings(final CorsRegistry registry) {
        registry.addMapping("/**")//addMapping 메서드는 CORS(Cross-Origin Resource Sharing) 설정에서 특정 경로에 대한 CORS 설정을 추가하도록 설계됨
                .allowedOrigins("*")//allowedOrigins("*"):이 메소드는 허용되는 출처를 설정
                .allowedMethods("OPTIONS", "GET", "POST", "PUT", "DELETE")//allowedMethods("OPTIONS", "GET", "POST", "PUT", "DELETE"): 이 메소드는 특정 HTTP 메소드를 사용하는 요청을 허용함. 여기서는 OPTIONS, GET, POST, PUT, DELETE 메소드를 사용하는 요청을 허용하도록 설정
                .exposedHeaders("Authorization");//exposedHeaders("Authorization"): 이 메소드는 브라우저가 접근할 수 있는 헤더를 설정함. \여기서는 "Authorization" 헤더를 노출하도록 설정함.이는 클라이언트가 Authorization 헤더의 내용을 읽을 수 있게 하기 위함
    }
}//클라이언트가 다른 도메인, 프로토콜 또는 포트에서 실행되는 서버 리소스에 접근할 수 있게 함