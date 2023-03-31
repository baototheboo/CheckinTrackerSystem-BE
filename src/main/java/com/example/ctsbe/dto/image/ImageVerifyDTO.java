package com.example.ctsbe.dto.image;

import com.example.ctsbe.constant.ApplicationConstant;
import com.example.ctsbe.entity.ImagesVerify;
import com.example.ctsbe.enums.FaceStatus;
import com.example.ctsbe.util.DateUtil;
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
    private String imagePath;
    @JsonProperty
    private LocalDateTime timeVerify;
    @JsonProperty
    private Double probability;
    @JsonProperty
    private Integer recognizeStaffId;
    @JsonProperty
    private FaceStatus status;
    @JsonProperty
    private String verifyDate;
    @JsonProperty
    private String verifyTime;


    public ImageVerifyDTO() {

    }

    public ImageVerifyDTO(Integer imageVerifyId, String image, Instant timeVerify, Double probability, Integer recognizeStaffId, FaceStatus status) {
        this.imageVerifyId = imageVerifyId;
        this.image = image;
        this.imagePath = image.replace("\\","/");
        this.timeVerify = LocalDateTime.from(timeVerify.atZone(ZoneId.of(ApplicationConstant.VN_TIME_ZONE)));
        this.probability = probability;
        this.recognizeStaffId = recognizeStaffId;
        this.status = status;
        this.verifyDate = DateUtil.convertTimeVerifyToStringDate(LocalDateTime.from(timeVerify.atZone(ZoneId.of(ApplicationConstant.VN_TIME_ZONE))));
        this.verifyTime = DateUtil.convertTimeVerifyToStringTime(LocalDateTime.from(timeVerify.atZone(ZoneId.of(ApplicationConstant.VN_TIME_ZONE))));
    }

    public ImageVerifyDTO(Integer imageVerifyId, String firstName, String lastName, String image, Instant timeVerify, Double probability, Integer recognizeStaffId, FaceStatus status) {
        this.imageVerifyId = imageVerifyId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.image = image;
        this.imagePath = image.replace("\\","/");
        this.timeVerify = LocalDateTime.from(timeVerify.atZone(ZoneId.of(ApplicationConstant.VN_TIME_ZONE)));
        this.probability = probability;
        this.recognizeStaffId = recognizeStaffId;
        this.status = status;
        this.verifyDate = DateUtil.convertTimeVerifyToStringDate(LocalDateTime.from(timeVerify.atZone(ZoneId.of(ApplicationConstant.VN_TIME_ZONE))));
        this.verifyTime = DateUtil.convertTimeVerifyToStringTime(LocalDateTime.from(timeVerify.atZone(ZoneId.of(ApplicationConstant.VN_TIME_ZONE))));
    }

    public ImagesVerify toEntity() {
        ImagesVerify imagesVerify = new ImagesVerify();
        imagesVerify.setImage(this.getImage());
        imagesVerify.setProbability(this.getProbability());
        imagesVerify.setTimeVerify(DateUtil.convertLocalDateTimeToInstant(this.getTimeVerify()));
        imagesVerify.setRecognizeStaffId(this.getRecognizeStaffId());
        imagesVerify.setStatus(this.getStatus());
        return imagesVerify;
    }
}
