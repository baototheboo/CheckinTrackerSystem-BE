package com.example.ctsbe.dto.vgg;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;
@Data
public class ImageDeleteVggDTO {
    @JsonProperty
    @NotNull
    private String staffId;

    public ImageDeleteVggDTO(String staffId) {
        this.staffId = staffId;
    }
}
