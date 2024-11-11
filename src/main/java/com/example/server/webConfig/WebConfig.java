package com.example.server.webConfig;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // /img/** 경로를 로컬 파일 디렉토리와 매핑
        registry.addResourceHandler("/img/**")
                .addResourceLocations("file:///Users/lee/Documents/server/src/main/resources/img/");
    }
}
