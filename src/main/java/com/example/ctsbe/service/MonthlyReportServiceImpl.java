package com.example.ctsbe.service;

import com.example.ctsbe.dto.monthlyReport.MonthlyReportDTO;
import com.example.ctsbe.dto.monthlyReport.MonthlyReportExport;
import com.example.ctsbe.entity.MonthlyReport;
import com.example.ctsbe.repository.MonthlyReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MonthlyReportServiceImpl implements MonthlyReportService{
    @Autowired
    private MonthlyReportRepository monthlyReportRepository;

    @Override
    public Page<MonthlyReport> getListByIdAndMonthYear(int staffId, String monthYear, Pageable pageable) {
        return monthlyReportRepository.getListReportByIdAndMonthYear(staffId, monthYear,pageable);
    }

    @Override
    public Page<MonthlyReport> getListByMonthYear(String monthYear, Pageable pageable) {
        return monthlyReportRepository.getListReportByMonthYear(monthYear, pageable);
    }

    @Override
    public List<MonthlyReport> getListReportExportByMonth(String monthYear) {
        return monthlyReportRepository.getReportByMonthYear(monthYear);
    }

    @Override
    public MonthlyReportExport export(List<MonthlyReportDTO> list) {
        return new MonthlyReportExport(list);
    }
}
