package com.example.ctsbe.entity;

import com.example.ctsbe.enums.FaceStatus;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "images_verify")
public class ImagesVerify {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Size(max = 255)
    @NotNull
    @Column(name = "image", nullable = false)
    private String image;

    @Column(name = "time_verify")
    private Instant timeVerify;

    @Column(name = "probability", precision = 2, scale = 2)
    private Double probability;

    @Column(name = "recognize_staff_id")
    private Integer recognizeStaffId;

    @Column(name = "device_id")
    private Integer deviceId;

    @Size(max = 255)
    @NotNull
    @Column(name = "status", nullable = false)
    private FaceStatus status;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Instant getTimeVerify() {
        return timeVerify;
    }

    public void setTimeVerify(Instant timeVerify) {
        this.timeVerify = timeVerify;
    }

    public Double getProbability() {
        return probability;
    }

    public void setProbability(Double probability) {
        this.probability = probability;
    }

    public Integer getRecognizeStaffId() {
        return recognizeStaffId;
    }

    public void setRecognizeStaffId(Integer recognizeStaffId) {
        this.recognizeStaffId = recognizeStaffId;
    }

    public Integer getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(Integer deviceId) {
        this.deviceId = deviceId;
    }

    public FaceStatus getStatus() {
        return status;
    }

    public void setStatus(FaceStatus status) {
        this.status = status;
    }

}