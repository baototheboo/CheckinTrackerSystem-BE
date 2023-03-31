package com.example.ctsbe.dto.vgg;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;
import java.util.List;

public class ImageSetupVggDTO {
    @JsonProperty
    @NotNull
    private List<String> imgs;
    private String timeVerify;

    public String getTimeVerify() {
        return timeVerify;
    }


    public void setTimeVerify(String timeVerify) {
        this.timeVerify = timeVerify;
    }

    public ImageSetupVggDTO() {
    }

    public ImageSetupVggDTO(List<String> imgs) {
        this.imgs = imgs;
    }

    public List<String> getImgs() {
        return imgs;
    }

    public void setImgs(List<String> imgs) {
        this.imgs = imgs;
    }

}
