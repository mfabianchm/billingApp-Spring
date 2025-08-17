package com.example.billingApp.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Paths;

//This class is for storing all images into our uploads folder (We can change it to AWS).

@Configuration
public class StaticResourceConfig implements WebMvcConfigurer {

    @Override
    public void  addResourceHandlers(ResourceHandlerRegistry registry) {
        String uploadDirectory = Paths.get("uploads").toAbsolutePath().toString();

        registry.addResourceHandler("/uploads/**").addResourceLocations("file:" + uploadDirectory+"/");
    }
}
