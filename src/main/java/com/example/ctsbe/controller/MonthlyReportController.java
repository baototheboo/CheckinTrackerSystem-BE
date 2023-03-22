package com.example.ctsbe.controller;

import com.example.ctsbe.service.MonthlyReportService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/reports")
@CrossOrigin(origins = "*")
public class MonthlyReportController {
    @Autowired
    private MonthlyReportService monthlyReportService;
}
