package com.example.ctsbe.controller;

import com.example.ctsbe.dto.account.AccountDTO;
import com.example.ctsbe.dto.staff.StaffAddDTO;
import com.example.ctsbe.dto.staff.StaffDTO;
import com.example.ctsbe.entity.Account;
import com.example.ctsbe.entity.Staff;
import com.example.ctsbe.mapper.AccountMapper;
import com.example.ctsbe.mapper.StaffMapper;
import com.example.ctsbe.service.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/staffs")
@CrossOrigin(origins = "*")
public class StaffController {
    @Autowired
    private StaffService staffService;

    @PostMapping("/addStaff")
    public String addStaff(@RequestBody StaffAddDTO dto) {
        try {
            staffService.addStaff(dto);
            return "Add successfully";
        } catch (Exception e) {
            return e.getMessage();
        }

    }

    @PutMapping("/changeEnableStaff/{id}")
    public ResponseEntity<?> changeEnableStaff(@PathVariable("id") int id){
        try{
            staffService.changeEnableStaff(id);
            return new ResponseEntity<>("Update enable status successfully",HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping("/getAllStaff")
    public ResponseEntity<Map<String, Object>> getAllStaff(@RequestParam(defaultValue = "1") int page
            , @RequestParam(defaultValue = "3") int size
            , @RequestParam(required = false) String name) {
        try {
            List<Staff> list = new ArrayList<>();
            Pageable pageable = PageRequest.of(page - 1, size);
            Page<Staff> staffPage;
            if (name == null) {
                staffPage = staffService.getAllStaff(pageable);
            } else {
                staffPage = staffService.getStaffByName(name,name,pageable);
            }
            list = staffPage.getContent();
            List<StaffDTO> listDto = list.stream().
                    map(StaffMapper::convertStaffToStaffDto).collect(Collectors.toList());
            Map<String, Object> response = new HashMap<>();
            response.put("list", listDto);
            response.put("currentPage", staffPage.getNumber());
            response.put("allProducts", staffPage.getTotalElements());
            response.put("allPages", staffPage.getTotalPages());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
