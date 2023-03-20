package com.example.ctsbe.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FacialRecognitionConfiguration {

    @Value("http://localhost:8080")
    private String facialRecognitionUri;

    public String getFacialRecognitionUri() {
        return facialRecognitionUri;
    }
}
