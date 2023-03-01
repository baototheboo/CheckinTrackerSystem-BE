package com.example.ctsbe.entity;

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
    private BigDecimal probability;

    @Column(name = "recognize_staff_id")
    private Integer recognizeStaffId;

    @Column(name = "device_id")
    private Integer deviceId;

    @Size(max = 255)
    @NotNull
    @Column(name = "status", nullable = false)
    private String status;

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

    public BigDecimal getProbability() {
        return probability;
    }

    public void setProbability(BigDecimal probability) {
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}