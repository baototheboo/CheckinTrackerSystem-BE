package com.example.ctsbe.mapper;

import com.example.ctsbe.dto.monthlyReport.MonthlyReportDTO;
import com.example.ctsbe.entity.MonthlyReport;
import com.example.ctsbe.util.DateUtil;

public class MonthlyReportMapper {
    public static MonthlyReportDTO convertEntityToDto(MonthlyReport monthlyReport){
        MonthlyReportDTO dto = new MonthlyReportDTO();
        DateUtil util = new DateUtil();
        dto.setStaffName(monthlyReport.getStaff().getFullName());
        dto.setMonthAndYear(util.convertLocalDateToMonthAndYear(monthlyReport.getMonth()));
        dto.setActiveDay(monthlyReport.getActiveDay());
        dto.setLateDay(monthlyReport.getLateDay());
        dto.setOffDay(monthlyReport.getOffDay());
        dto.setWorkingHour(monthlyReport.getWorkingHour());
        return dto;
    }
}
