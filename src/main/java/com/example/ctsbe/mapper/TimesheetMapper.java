package com.example.ctsbe.mapper;

import com.example.ctsbe.dto.timesheet.TimesheetDTO;
import com.example.ctsbe.entity.Timesheet;
import com.example.ctsbe.util.DateUtil;

public class TimesheetMapper {
    public static TimesheetDTO convertEntityToDto(Timesheet timesheet){
        DateUtil dateUtil = new DateUtil();
        TimesheetDTO dto = new TimesheetDTO();
        dto.setStaffId(timesheet.getStaff().getId());
        dto.setStaffName(timesheet.getStaff().getFullName());
        dto.setMonthYear(dateUtil.convertLocalDateToMonthAndYear(timesheet.getDate()));
        return dto;
    }
}
