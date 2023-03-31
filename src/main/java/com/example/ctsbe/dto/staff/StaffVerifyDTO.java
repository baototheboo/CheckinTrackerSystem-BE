package com.example.ctsbe.dto.staff;

import com.example.ctsbe.enums.FacialRecognitionStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class StaffVerifyDTO {
    @JsonProperty("firstName")
    private String firstName;
    @JsonProperty("lastName")
    private String lastName;
    @JsonProperty
    private FacialRecognitionStatus facialRecognitionStatus;
    @JsonProperty("identityVerified")
    private boolean identityVerified;
    @JsonProperty("probability")
    private Float probability;
    @JsonProperty("showMessage")
    private Boolean showMessage;
    @JsonProperty
    private Integer imageVerifyId;
    @JsonProperty
    private Integer staffId;
}
