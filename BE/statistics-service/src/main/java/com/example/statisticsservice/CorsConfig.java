package com.example.statisticsservice;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")
                .allowedOrigins("http://localhost:3000") // İzin verilen origin URL'ini burada belirtin
                .allowedMethods("GET", "POST", "PUT", "DELETE") // İzin verilen HTTP metotlarını belirtin
                .allowCredentials(true);
    }
}