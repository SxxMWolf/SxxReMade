package com.example.record;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    ApiKeyInterceptor apiKeyInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 기존: /api/image/** 만
        // 보강: 실제 컨트롤러가 /generate-image 라우트라면 함께 보호
        registry.addInterceptor(apiKeyInterceptor)
                .addPathPatterns("/api/image/**", "/generate-image");
    }
}
