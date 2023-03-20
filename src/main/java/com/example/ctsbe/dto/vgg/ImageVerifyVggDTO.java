package com.example.ctsbe.dto.vgg;

import com.example.ctsbe.dto.vgg.ImageSetupVggDTO;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

public class ImageVerifyVggDTO {
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @JsonProperty
    @NotNull
    private LocalDateTime currentDateTime;

    @JsonProperty
    @NotNull
    private ImageSetupVggDTO imageSetupVggDTO;

    public LocalDateTime getCurrentDateTime() {
        return currentDateTime;
    }

    public void setCurrentDateTime(LocalDateTime currentDateTime) {
        this.currentDateTime = currentDateTime;
    }

    public ImageSetupVggDTO getImageSetupDTO() {
        return imageSetupVggDTO;
    }

    public void setImageSetupDTO(ImageSetupVggDTO imageSetupVggDTO) {
        this.imageSetupVggDTO = imageSetupVggDTO;
    }
}
