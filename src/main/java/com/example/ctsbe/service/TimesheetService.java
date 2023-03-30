package com.example.ctsbe.service;

import com.example.ctsbe.dto.timesheet.TimesheetDTO;
import com.example.ctsbe.entity.Timesheet;

import java.time.LocalDate;
import java.util.List;

public interface TimesheetService {
    List<Timesheet> getTimesheetByStaffIdAndMonth(int staffId,String month);

    TimesheetDTO checkDayStatus(List<Timesheet> list,int staffId);

    List<Integer> getListDayOfLocalDate(List<Timesheet> list);
}
