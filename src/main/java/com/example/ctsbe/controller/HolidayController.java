package com.example.ctsbe.controller;

import com.example.ctsbe.dto.group.GroupDetailDTO;
import com.example.ctsbe.dto.project.ProjectInProfileDTO;
import com.example.ctsbe.entity.Group;
import com.example.ctsbe.entity.Project;
import com.example.ctsbe.mapper.GroupMapper;
import com.example.ctsbe.mapper.ProjectMapper;
import com.example.ctsbe.service.HolidayService;
import com.example.ctsbe.util.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/holidays")
@CrossOrigin(origins = "*")
public class HolidayController {
    @Autowired
    private HolidayService holidayService;
    DateUtil dateUtil = new DateUtil();
    @GetMapping("/check")
    public ResponseEntity<?> getGroupDetail(@RequestParam String date) {
        try{
            boolean check = holidayService.checkHoliday(dateUtil.convertStringToLocalDate(date));
            return new ResponseEntity<>(check, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
