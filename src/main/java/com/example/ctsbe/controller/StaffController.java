package com.example.ctsbe.controller;

import com.example.ctsbe.dto.AccountAddDTO;
import com.example.ctsbe.dto.StaffDTO;
import com.example.ctsbe.entity.Account;
import com.example.ctsbe.service.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/staff")
public class StaffController {
    @Autowired
    private StaffService staffService;

    @PostMapping("/addStaff")
    public String addStaff(@RequestBody StaffDTO dto){
        try{
            staffService.addStaff(dto);
            return "Add successfully";
        }catch (Exception e){
            return e.getMessage();
        }

    }
}
