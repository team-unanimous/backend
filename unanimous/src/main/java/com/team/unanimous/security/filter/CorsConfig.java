package com.team.unanimous.security.filter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig {//크로스 오리진 정책 설정

    @Bean
    public CorsFilter corsFilter(){
        UrlBasedCorsConfigurationSource source=new UrlBasedCorsConfigurationSource();
        CorsConfiguration config=new CorsConfiguration();
        config.setAllowCredentials(true);// 내 서버가 응답을 할 때 Json을 자바스크립트에서 처리할 수 있게 할지를 결정하는 것
        config.addAllowedOrigin("http://localhost:3000");
        config.addAllowedOrigin("http://dailyratingassingment.s3-website.ap-northeast-2.amazonaws.com/");
        config.addAllowedOrigin("http://13.125.57.223/");
        config.addAllowedOrigin("https://dkworld.shop");
        config.addAllowedOrigin("https://sparta-ysh.shop");
        config.addAllowedOrigin("https://unanimous.co.kr");
        config.addAllowedOrigin("http://unanimous.co.kr.s3-website.ap-northeast-2.amazonaws.com/");
        config.addAllowedOrigin("");//모든 ip에 응답을 허용
        config.setAllowCredentials(true);
        config.addAllowedHeader("*");//모든 헤더에 응답 허용
        config.addAllowedMethod("*");//모든 http 메서드 응답 허용
        config.addExposedHeader("*");
        source.registerCorsConfiguration("/**",config);

        return new CorsFilter(source);
    }
}