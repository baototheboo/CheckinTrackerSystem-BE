package com.example.ctsbe.dto.staff;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Data
public class StaffSetupDTO {
    @JsonProperty
    private int staffId;
    @JsonProperty
    private String staffName;
    @JsonProperty
    private List<String> imgs;

    public StaffSetupDTO(int staffId,
                             String staffName,
                             List<String> imgs) {
        this.staffId = staffId;
        this.staffName = staffName;
        this.imgs = imgs;
    }
}
