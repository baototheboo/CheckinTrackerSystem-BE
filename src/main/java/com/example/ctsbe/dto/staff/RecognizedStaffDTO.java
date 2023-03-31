package com.example.ctsbe.dto.staff;

import com.example.ctsbe.enums.EmotionEnum;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class RecognizedStaffDTO {
    @JsonProperty
    private String partId;
    @JsonProperty
    private String probability;
    @JsonProperty
    private EmotionEnum emotion;


    public Float getProbability() {
        if (probability == null) {
            return 0F;
        }
        return Float.parseFloat(probability);
    }

    @Override
    public String toString() {
        return "RecognizedStaffDTO{" +
                "partId='" + partId + '\'' +
                ", probability='" + probability + '\'' +
                '}';
    }
}
