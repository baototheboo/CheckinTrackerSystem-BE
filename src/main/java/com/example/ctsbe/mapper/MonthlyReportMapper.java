package com.example.ctsbe.mapper;

import com.example.ctsbe.dto.monthlyReport.MonthlyReportDTO;
import com.example.ctsbe.entity.MonthlyReport;
import com.example.ctsbe.util.DateUtil;

public class MonthlyReportMapper {
    public MonthlyReportDTO convertEntityToDto(MonthlyReport monthlyReport){
        MonthlyReportDTO dto = new MonthlyReportDTO();
        DateUtil util = new DateUtil();
        dto.setStaffName(monthlyReport.getStaff().getSurname()
                +" "+ monthlyReport.getStaff().getFirstName());
        dto.setMonthAndYear(util.convertLocalDateToMonthAndYear(monthlyReport.getMonth()));
        dto.setActiveDay(dto.getActiveDay());
        dto.setLateDay(dto.getLateDay());
        dto.setOffDay(dto.getOffDay());
        return dto;
    }
}
