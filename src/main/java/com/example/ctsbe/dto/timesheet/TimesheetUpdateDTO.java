package com.example.ctsbe.dto.timesheet;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDate;

@Data
public class TimesheetUpdateDTO {

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @JsonProperty
    private LocalDate date;

    @JsonProperty
    private String dateStatus;

    @JsonProperty
    private String note;

    @JsonProperty
    private String dayWorkingStatus;

    @JsonProperty
    public void setDayWorkingStatus(String dayWorkingStatus) {
        this.dayWorkingStatus = (dayWorkingStatus == null) ? "Làm cả ngày" : dayWorkingStatus;
    }
}
