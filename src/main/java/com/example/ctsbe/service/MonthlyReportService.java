package com.example.ctsbe.service;

import com.example.ctsbe.entity.MonthlyReport;
import com.example.ctsbe.repository.MonthlyReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface MonthlyReportService {
    Page<MonthlyReport> getListByIdAndMonthYear(int staffId, String monthYear, Pageable pageable);
}
