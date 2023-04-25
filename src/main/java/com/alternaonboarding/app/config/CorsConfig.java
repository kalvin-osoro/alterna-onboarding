package com.alternaonboarding.app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig {
    @Bean
    public WebMvcConfigurer webMvcConfigurer() {
        return new WebMvcConfigurer() {
           @Override
            public void addCorsMappings(CorsRegistry registry) {
               registry.addMapping("/api/**")
//                       .allowedOrigins("http://localhost:4200")
                       .allowedOrigins("*")
                       .allowedMethods("GET", "POST", "PUT", "DELETE")
                       .allowedHeaders("*")
                       .allowCredentials(true)
                       .maxAge(3600);
           }
        };

    }
}
