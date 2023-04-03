package com.example.ctsbe.controller;

import com.example.ctsbe.dto.staff.StaffAvailableDTO;
import com.example.ctsbe.dto.timesheet.TimesheetDTO;
import com.example.ctsbe.entity.Staff;
import com.example.ctsbe.entity.Timesheet;
import com.example.ctsbe.mapper.StaffMapper;
import com.example.ctsbe.service.TimesheetService;
import com.example.ctsbe.util.DateUtil;
import com.example.ctsbe.util.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/timesheets")
@CrossOrigin(origins = "*")
public class TimesheetController {
    @Autowired
    private TimesheetService timesheetService;
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    DateUtil dateUtil = new DateUtil();

    @GetMapping("/getTimesheet/{staffId}")
    public ResponseEntity<Map<String, Object>> getTimesheet(
            @PathVariable("staffId") int staffId, @RequestParam String yearMonth
    ) {
        try {
            List<Timesheet> list = timesheetService.getTimesheetByStaffIdAndMonth(staffId, yearMonth);
            TimesheetDTO dto = timesheetService.checkDayStatus(list, staffId, yearMonth);
            Map<String, Object> response = new HashMap<>();
            response.put("dto", dto);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("exception", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/getYourOwnTimesheet")
    public ResponseEntity<Map<String, Object>> getYourOwnTimesheet(@RequestParam(required = false) String yearMonth) {
        try {
            int id = getIdFromToken();
            if (yearMonth == null) {
                yearMonth = dateUtil.convertLocalDateToMonthAndYear(LocalDate.now());
            }
            List<Timesheet> list = timesheetService.getTimesheetByStaffIdAndMonth(id, yearMonth);
            TimesheetDTO dto = timesheetService.checkDayStatus(list, id, yearMonth);
            Map<String, Object> response = new HashMap<>();
            response.put("dto", dto);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("exception", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    public int getIdFromToken() {
        final String requestTokenHeader = request.getHeader("Authorization");
        String jwtToken = null;
        if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
            jwtToken = requestTokenHeader.substring(7);
        }
        int id = jwtTokenUtil.getIdFromToken(jwtToken);
        return id;
    }
}
