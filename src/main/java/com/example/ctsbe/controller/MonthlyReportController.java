package com.example.ctsbe.controller;

import com.example.ctsbe.dto.monthlyReport.MonthlyReportDTO;
import com.example.ctsbe.dto.monthlyReport.MonthlyReportExport;
import com.example.ctsbe.dto.staffProject.StaffInProjectDTO;
import com.example.ctsbe.dto.timesheet.TimesheetDTO;
import com.example.ctsbe.entity.MonthlyReport;
import com.example.ctsbe.entity.Project;
import com.example.ctsbe.entity.StaffProject;
import com.example.ctsbe.entity.Timesheet;
import com.example.ctsbe.mapper.MonthlyReportMapper;
import com.example.ctsbe.mapper.StaffProjectMapper;
import com.example.ctsbe.service.MonthlyReportService;
import com.example.ctsbe.service.TimesheetService;
import com.example.ctsbe.util.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/reports")
@CrossOrigin(origins = "*")
public class MonthlyReportController {
    @Autowired
    private MonthlyReportService monthlyReportService;
    @Autowired
    private TimesheetService timesheetService;

    @GetMapping("/getMonthlyReport")
    @RolesAllowed("ROLE_HUMAN RESOURCE")
    public ResponseEntity<Map<String, Object>> getMonthlyReport(@RequestParam(defaultValue = "1") int page
            , @RequestParam(defaultValue = "3") int size
            //, @RequestParam(defaultValue = "0") int staffId
            , @RequestParam(required = false) String monthYear) {
        try{
            List<MonthlyReport> list = new ArrayList<>();
            Pageable pageable = PageRequest.of(page - 1, size);
            Page<MonthlyReport> reportsPage;
            if(monthYear == null) {
                monthYear = new DateUtil().convertLocalDateToMonthAndYear(LocalDate.now());
            }
            reportsPage = monthlyReportService.getListByMonthYear(monthYear, pageable);
            list = reportsPage.getContent();
            List<MonthlyReportDTO> listDto = list.stream().
                    map(MonthlyReportMapper::convertEntityToDto).collect(Collectors.toList());
            Map<String, Object> response = new HashMap<>();
            response.put("list", listDto);
            response.put("currentPage", reportsPage.getNumber());
            response.put("allProducts", reportsPage.getTotalElements());
            response.put("allPages", reportsPage.getTotalPages());
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/addToMonthlyReport")
    public ResponseEntity<?> addToMonthlyReport(@RequestParam(required = false) String monthYear){
        try{
            if(monthYear == null) {
                monthYear = new DateUtil().convertLocalDateToMonthAndYear(LocalDate.now());
            }

            return new ResponseEntity<>("Add thành công", HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/export")
    //@RolesAllowed("ROLE_HUMAN RESOURCE")
    public void exportFile(@RequestParam String monthYear, HttpServletResponse response) throws IOException, ParseException {
        List<TimesheetDTO> list =  timesheetService.getListTimeSheetByMonth(monthYear);
        monthlyReportService.addToMonthlyReport(list);

        response.setContentType("application/octet-stream");
        String headerKey = "Content-Disposition";
        String headerValue = "attachment;filename=Tong_hop_"+monthYear+".xlsx";
        response.setHeader(headerKey, headerValue);

        List<TimesheetDTO> listTimeSheet =  timesheetService.getListTimeSheetByMonth(monthYear);
        List<MonthlyReport> listReport = monthlyReportService.getListReportExportByMonth(monthYear);
        List<MonthlyReportDTO> listDto = listReport.stream()
                .map(MonthlyReportMapper::convertEntityToDto).collect(Collectors.toList());
        MonthlyReportExport exporter = monthlyReportService.export(listDto,listTimeSheet);
        exporter.export(response);
    }
}
