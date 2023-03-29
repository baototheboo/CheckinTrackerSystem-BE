package com.example.ctsbe.controller;

import com.example.ctsbe.dto.complaint.ComplaintAddDTO;
import com.example.ctsbe.dto.complaint.ComplaintDTO;
import com.example.ctsbe.entity.Complaint;
import com.example.ctsbe.mapper.ComplaintMapper;
import com.example.ctsbe.service.ComplaintService;
import com.example.ctsbe.util.JwtTokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/complaints")
public class ComplaintController {
    @Autowired
    private ComplaintService complaintService;
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @PostMapping("/sendComplaint")
    @RolesAllowed({"ROLE_STAFF", "ROLE_GROUP LEADER", "ROLE_PROJECT MANAGER"})
    public ResponseEntity<?> sendComplaint(@Valid @RequestBody ComplaintAddDTO dto) {
        try {
            complaintService.addComplaint(dto);
            return new ResponseEntity<>("Gửi đơn thành công", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/getAllComplaints")
    public ResponseEntity<Map<String, Object>> getAllComplaints(@RequestParam(defaultValue = "1") int page
            , @RequestParam(defaultValue = "3") int size
            , @RequestParam(required = false) String status) {
        try {
            List<Complaint> list = new ArrayList<>();
            Pageable pageable = PageRequest.of(page - 1, size);
            Page<Complaint> complaintPage;
            if(status == null) complaintPage = complaintService.getListComplaint(pageable);
            else complaintPage = complaintService.getListComplaintByStatus(status,pageable);
            list = complaintPage.getContent();
            List<ComplaintDTO> listDto = list.stream().
                    map(ComplaintMapper::convertEntityToDto).collect(Collectors.toList());
            Map<String, Object> response = new HashMap<>();
            response.put("list", listDto);
            response.put("currentPage", complaintPage.getNumber());
            response.put("allProducts", complaintPage.getTotalElements());
            response.put("allPages", complaintPage.getTotalPages());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("exception", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/updateComplaint/{id}/{status}")
    @RolesAllowed("ROLE_HUMAN RESOURCE")
    public ResponseEntity<?> updateAccount(@PathVariable("id") int id, @PathVariable("status") int status) {
        try {
            complaintService.updateComplaint(id, status);
            return new ResponseEntity<>("Cập nhật đơn thành công!", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }


}
