package com.example.ctsbe.dto.image;

import com.example.ctsbe.constant.ApplicationConstant;
import com.example.ctsbe.entity.ImagesSetup;
import com.example.ctsbe.entity.Staff;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.UUID;

@Data
public class ImageSetupDTO {
    @JsonProperty
    private Integer imagesSetupId;
    @JsonProperty
    private String image;
    @JsonProperty
    private LocalDateTime timeSetup;
    @JsonProperty
    private Integer staffId;
    @JsonProperty
    private String staffName;
    @JsonProperty
    private String status;

    @JsonProperty
    private String firstName;

    @JsonProperty
    private String surname;

    @JsonProperty
    private LocalDateTime createDate;
    @JsonProperty
    private LocalDateTime updatedDate;

    public ImageSetupDTO(String image, LocalDateTime timeSetup, Integer staffId, String staffName, String status,
                          LocalDateTime createDate, LocalDateTime updatedDate) {
        this.image = image;
        this.timeSetup = timeSetup;
        this.staffId = staffId;
        this.staffName = staffName;
        this.status = status;
        this.createDate = createDate;
        this.updatedDate = updatedDate;
    }

    public ImageSetupDTO(Integer imagesSetupId, String image, Instant timeSetup, Integer staffId, String status, String firstName, String surname) {
        this.imagesSetupId = imagesSetupId;
        this.image = image;
        this.timeSetup = timeSetup.atZone(ZoneId.of(ApplicationConstant.VN_TIME_ZONE)).toLocalDateTime();
        this.staffId = staffId;
        this.staffName = surname + " " + firstName;
        this.status = status;
        this.firstName = firstName;
        this.surname = surname;
    }

    public ImagesSetup toEntity() {
        ImagesSetup imagesSetup = new ImagesSetup();
        imagesSetup.setId(this.getImagesSetupId());
        imagesSetup.setImage(this.getImage());
        imagesSetup.setTimeSetup(this.getTimeSetup().toInstant(ZoneOffset.UTC));
        imagesSetup.setStatus(this.getStatus());
        Staff staff = new Staff();
        staff.setId(this.getStaffId());
        imagesSetup.setStaff(staff);
        imagesSetup.setCreatedDate(this.getCreateDate().toInstant(ZoneOffset.UTC));
        imagesSetup.setLastUpdated(this.getUpdatedDate().toInstant(ZoneOffset.UTC));
        return imagesSetup;
    }
}
