package com.example.ctsbe.controller;

import com.example.ctsbe.dto.staff.StaffAvailableDTO;
import com.example.ctsbe.dto.timesheet.TimesheetDTO;
import com.example.ctsbe.entity.Staff;
import com.example.ctsbe.entity.Timesheet;
import com.example.ctsbe.mapper.StaffMapper;
import com.example.ctsbe.service.TimesheetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/timesheets")
@CrossOrigin(origins = "*")
public class TimesheetController {
    @Autowired
    private TimesheetService timesheetService;

    @GetMapping("/getTimesheet")
    public ResponseEntity<Map<String, Object>> getTimesheet(
            @RequestParam int staffId, @RequestParam String yearMonth
    ){
        try{
            List<Timesheet> list = timesheetService.getTimesheetByStaffIdAndMonth(staffId, yearMonth);
            TimesheetDTO dto = timesheetService.checkDayStatus(list,staffId);
            Map<String, Object> response = new HashMap<>();
            response.put("dto", dto);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (Exception e){
            Map<String, Object> response = new HashMap<>();
            response.put("exception", e.getMessage());
            return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
        }
    }
}
