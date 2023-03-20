package com.example.ctsbe.dto.image;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class ImageSetupDTO {
    @JsonProperty
    private UUID imagesSetupId;
    @JsonProperty
    private String image;
    @JsonProperty
    private LocalDateTime timeSetup;
    @JsonProperty
    private UUID employeeId;
    @JsonProperty
    private String employeeName;
    @JsonProperty
    private Boolean active;
    @JsonProperty
    private LocalDateTime createDate;
    @JsonProperty
    private LocalDateTime updatedDate;
}
