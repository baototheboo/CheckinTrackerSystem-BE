package com.example.ctsbe.dto.image;

import com.example.ctsbe.entity.ImagesVerify;
import com.example.ctsbe.enums.FaceStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Data
public class ImageVerifyDTO {
    @JsonProperty
    private Integer imageVerifyId;
    @JsonProperty
    private String name;
    @JsonProperty
    private String recognizeName;
    @JsonProperty
    private String image;
    @JsonProperty
    private LocalDateTime timeVerify;
    @JsonProperty
    private Double probability;
    @JsonProperty
    private Integer recognizeStaffId;
    @JsonProperty
    private FaceStatus status;
    @JsonProperty
    private String firstName;
    @JsonProperty
    private String lastName;

    public ImagesVerify toEntity() {
        ImagesVerify imagesVerify = new ImagesVerify();
        imagesVerify.setImage(this.getImage());
        imagesVerify.setProbability(this.getProbability());
        imagesVerify.setTimeVerify(this.getTimeVerify().toInstant(ZoneOffset.UTC));
        imagesVerify.setRecognizeStaffId(this.getRecognizeStaffId());
        imagesVerify.setStatus(this.getStatus());
        return imagesVerify;
    }
}
