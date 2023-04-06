package com.example.ctsbe.service;

import com.example.ctsbe.dto.timesheet.TimesheetDTO;
import com.example.ctsbe.dto.timesheet.TimesheetResponseDTO;
import com.example.ctsbe.dto.timesheet.TimesheetUpdateDTO;
import com.example.ctsbe.entity.Staff;
import com.example.ctsbe.entity.Timesheet;

import java.time.LocalDate;
import java.util.List;

public interface TimesheetService {
    List<Timesheet> getTimesheetByStaffIdAndMonth(int staffId,String month);

    TimesheetDTO checkDayStatus(List<Timesheet> list,int staffId,String month);

    List<Integer> getListDayOfLocalDate(List<Timesheet> list);

    TimesheetResponseDTO getTimesheetByStaffAndDate(int staffId, LocalDate date);
    void updateTimesheetStatus(int staffId, TimesheetUpdateDTO timesheetUpdateDTO);

    List<TimesheetDTO> getListTimeSheetByMonth(String monthYear);
}
