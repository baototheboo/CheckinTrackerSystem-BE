package com.example.ctsbe.dto.timesheet;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TimesheetDTO {
    private Integer staffId;
    private String staffName;
    private String monthYear;
    private Double workingHours;
    private List<Integer> dayCheck;
}
