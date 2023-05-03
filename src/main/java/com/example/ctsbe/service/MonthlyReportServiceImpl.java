package com.example.ctsbe.service;

import com.example.ctsbe.dto.monthlyReport.MonthlyReportAddDTO;
import com.example.ctsbe.dto.monthlyReport.MonthlyReportDTO;
import com.example.ctsbe.dto.monthlyReport.MonthlyReportExport;
import com.example.ctsbe.dto.timesheet.TimesheetDTO;
import com.example.ctsbe.entity.MonthlyReport;
import com.example.ctsbe.entity.Timesheet;
import com.example.ctsbe.repository.MonthlyReportRepository;
import com.example.ctsbe.repository.StaffRepository;
import com.example.ctsbe.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Service
public class MonthlyReportServiceImpl implements MonthlyReportService {
    @Autowired
    private MonthlyReportRepository monthlyReportRepository;
    @Autowired
    private StaffRepository staffRepository;

    DateUtil dateUtil = new DateUtil();

    @Override
    public Page<MonthlyReport> getListByIdAndMonthYear(int staffId, String monthYear, Pageable pageable) {
        return monthlyReportRepository.getListReportByIdAndMonthYear(staffId, monthYear, pageable);
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
    public MonthlyReportExport export(List<MonthlyReportDTO> list,List<TimesheetDTO> listTimesheet) {
        return new MonthlyReportExport(list,listTimesheet);
    }

    @Override
    public List<MonthlyReportAddDTO> getListMonthlyReportToAdd(List<TimesheetDTO> list) {

        return null;
    }

    @Override
    public void addToMonthlyReport(List<TimesheetDTO> list) {
        int lateDay = 0, activeDay = 0, dayOff = 0;
        for (TimesheetDTO dto : list) {
            if(!(dto.getDayCheck() == null)){
                for (int status : dto.getDayCheck()) {
                    switch (status) {
                        case 1:
                            activeDay++;
                            break;
                        case 2:
                            lateDay++;
                            break;
                        case 3:
                            dayOff++;
                            break;
                    }
                }
                MonthlyReport existed = monthlyReportRepository.getMonthlyReportByMonthAndId(dto.getStaffId(),dto.getMonthYear());
                if(existed == null){
                    MonthlyReport monthlyReport = new MonthlyReport();
                    monthlyReport.setStaff(staffRepository.findStaffById(dto.getStaffId()));
                    monthlyReport.setMonth(dateUtil.convertStringToLocalDate(dto.getMonthYear() + "-01"));
                    //active day la so ngay di lam
                    monthlyReport.setActiveDay(activeDay + lateDay);
                    //late day la so ngay di lam muon
                    monthlyReport.setLateDay(lateDay);
                    monthlyReport.setOffDay(dayOff);
                    monthlyReport.setWorkingHour(dto.getWorkingHours().intValue());
                    monthlyReport.setCreatedDate(Instant.now());
                    monthlyReportRepository.save(monthlyReport);
                }else{
                    //active day la so ngay di lam
                    existed.setActiveDay(activeDay + lateDay);
                    //late day la so ngay di lam muon
                    existed.setLateDay(lateDay);
                    existed.setOffDay(dayOff);
                    existed.setWorkingHour(dto.getWorkingHours().intValue());
                    monthlyReportRepository.save(existed);
                }
                lateDay = activeDay = dayOff = 0;
            }
        }
    }

    @Override
    public MonthlyReport getMonthlyReportByIdAndMonth(int staffId, String yearMonth) {
        return monthlyReportRepository.getMonthlyReportByMonthAndId(staffId, yearMonth);
    }
}
