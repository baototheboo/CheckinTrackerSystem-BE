package com.example.ctsbe.service;

import com.example.ctsbe.dto.monthlyReport.MonthlyReportAddDTO;
import com.example.ctsbe.dto.monthlyReport.MonthlyReportDTO;
import com.example.ctsbe.dto.monthlyReport.MonthlyReportExport;
import com.example.ctsbe.dto.timesheet.TimesheetDTO;
import com.example.ctsbe.entity.MonthlyReport;
import com.example.ctsbe.repository.MonthlyReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

public interface MonthlyReportService {
    Page<MonthlyReport> getListByIdAndMonthYear(int staffId, String monthYear, Pageable pageable);

    Page<MonthlyReport> getListByMonthYear(String monthYear, Pageable pageable);

    List<MonthlyReport> getListReportExportByMonth(String monthYear);

    MonthlyReportExport export(List<MonthlyReportDTO> list,List<TimesheetDTO> listTimesheet);

    List<MonthlyReportAddDTO> getListMonthlyReportToAdd(List<TimesheetDTO> list);

    void addToMonthlyReport(List<TimesheetDTO> list);
}
