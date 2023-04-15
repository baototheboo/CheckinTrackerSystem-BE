package com.example.ctsbe.config;

import com.example.ctsbe.constant.ApplicationConstant;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FacialRecognitionConfiguration {

    @Value(ApplicationConstant.VGG_URL_AWS)
    private String facialRecognitionUri;

    public String getFacialRecognitionUri() {
        return facialRecognitionUri;
    }
}
