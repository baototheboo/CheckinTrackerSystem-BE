package com.example.ctsbe.dto.timesheet;

import com.example.ctsbe.entity.ImagesVerify;
import com.example.ctsbe.entity.Staff;
import com.example.ctsbe.entity.Timesheet;
import com.example.ctsbe.util.DateUtil;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.apache.poi.ss.formula.functions.T;


import java.time.Instant;
import java.time.LocalDate;

@Data
public class TimesheetResponseDTO {

    @JsonProperty
    private Integer id;

    @JsonProperty
    private Integer staffId;

    @JsonProperty
    private String firstName;

    @JsonProperty
    private String lastName;

    @JsonProperty
    private LocalDate date;

    @JsonProperty
    private String timeCheckIn;

    @JsonProperty
    private String  timeCheckOut;

    @JsonProperty
    private String dateStatus;

    @JsonProperty
    private String note;

    @JsonProperty
    private Double workingHours;

    @JsonProperty
    private String dayWorkingStatus;

    @JsonProperty
    private Double lateCheckInMinutes;

    @JsonProperty
    private Double earlyCheckOutMinutes;


    @JsonProperty
    private String updatedHistory;

    @JsonProperty
    private String lastUpdated;
    public  TimesheetResponseDTO() {

    }

    public TimesheetResponseDTO(Integer id, Integer staffId, String firstName, String lastName,
                                LocalDate date, Instant timeCheckIn, Instant timeCheckOut, String dateStatus,
                                String note, Double workingHours, String dayWorkingStatus,
                                Double lateCheckInMinutes, Double earlyCheckOutMinutes,
                                String updatedHistory, Instant lastUpdated) {
        this.id = id;
        this.staffId = staffId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.date = date;
        this.timeCheckIn = (timeCheckIn==null)?"":DateUtil.convertInstantToTimeVerifyString(timeCheckIn);
        this.timeCheckOut = (timeCheckOut==null)?"":DateUtil.convertInstantToTimeVerifyString(timeCheckOut);
        this.dateStatus = dateStatus;
        this.note = note;
        this.workingHours = workingHours;
        this.dayWorkingStatus = dayWorkingStatus;
        this.lateCheckInMinutes = lateCheckInMinutes;
        this.earlyCheckOutMinutes = earlyCheckOutMinutes;
        this.updatedHistory = updatedHistory;
        this.lastUpdated = DateUtil.convertInstantToTimeVerifyString(lastUpdated);
    }
}
