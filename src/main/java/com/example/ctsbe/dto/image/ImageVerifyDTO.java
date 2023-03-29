package com.example.ctsbe.dto.image;

import com.example.ctsbe.entity.ImagesVerify;
import com.example.ctsbe.enums.FaceStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
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
    private String firstName;
    @JsonProperty
    private String lastName;
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


    public ImageVerifyDTO() {

    }

    public ImageVerifyDTO(Integer imageVerifyId, String image, Instant timeVerify, Double probability, Integer recognizeStaffId, FaceStatus status) {
        this.imageVerifyId = imageVerifyId;
        this.image = image;
        this.timeVerify = LocalDateTime.from(timeVerify.atZone(ZoneId.of("Asia/Ho_Chi_Minh")));
        this.probability = probability;
        this.recognizeStaffId = recognizeStaffId;
        this.status = status;
    }

    public ImageVerifyDTO(Integer imageVerifyId, String firstName, String lastName, String image, Instant timeVerify, Double probability, Integer recognizeStaffId, FaceStatus status) {
        this.imageVerifyId = imageVerifyId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.image = image;
        this.timeVerify = LocalDateTime.from(timeVerify.atZone(ZoneId.of("Asia/Ho_Chi_Minh")));
        this.probability = probability;
        this.recognizeStaffId = recognizeStaffId;
        this.status = status;
    }

    public ImagesVerify toEntity() {
        ImagesVerify imagesVerify = new ImagesVerify();
        imagesVerify.setImage(this.getImage());
        imagesVerify.setProbability(this.getProbability());
        imagesVerify.setTimeVerify(this.getTimeVerify().atZone(ZoneId.of("Asia/Ho_Chi_Minh")).toInstant());
        imagesVerify.setRecognizeStaffId(this.getRecognizeStaffId());
        imagesVerify.setStatus(this.getStatus());
        return imagesVerify;
    }
}
