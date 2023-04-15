package com.example.ctsbe.dto.monthlyReport;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MonthlyReportAddDTO {
    private Integer staffId;
    private String monthAndYear;
    private Integer activeDay;
    private Integer lateDay;
    private Integer offDay;
    private Integer workingHour;
}
