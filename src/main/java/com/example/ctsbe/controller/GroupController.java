package com.example.ctsbe.controller;

import com.example.ctsbe.dto.group.GroupDTO;
import com.example.ctsbe.dto.group.GroupUpdateDTO;
import com.example.ctsbe.dto.staff.StaffDTO;
import com.example.ctsbe.entity.Group;
import com.example.ctsbe.entity.Staff;
import com.example.ctsbe.mapper.GroupMapper;
import com.example.ctsbe.mapper.StaffMapper;
import com.example.ctsbe.service.GroupService;
import com.example.ctsbe.service.StaffService;
import javassist.NotFoundException;
import lombok.extern.slf4j.Slf4j;
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

@Slf4j
@RestController
@RequestMapping("/groups")
@CrossOrigin(origins = "*")
public class GroupController {
    @Autowired
    private GroupService groupService;
    @Autowired
    private StaffService staffService;

    @GetMapping("/getAllGroups")
    public ResponseEntity<Map<String, Object>> getAllGroups(@RequestParam(defaultValue = "1") int page
            , @RequestParam(defaultValue = "3") int size
            , @RequestParam(required = false) String groupName) {
        try {
            List<Group> list = new ArrayList<>();
            Pageable pageable = PageRequest.of(page - 1, size);
            Page<Group> groupPage;
            if (groupName == null) {
                groupPage = groupService.getAllGroup(pageable);
            } else {
                groupPage = groupService.getAllGroupByName(groupName, pageable);
            }
            list = groupPage.getContent();
            List<GroupDTO> listDto = list.stream().
                    map(GroupMapper::convertGroupToGroupDTO).collect(Collectors.toList());
            Map<String, Object> response = new HashMap<>();
            response.put("list", listDto);
            response.put("currentPage", groupPage.getNumber());
            response.put("allProducts", groupPage.getTotalElements());
            response.put("allPages", groupPage.getTotalPages());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getAllStaffInGroup")
    public ResponseEntity<Map<String, Object>> getAllStaffInGroup(@RequestParam(defaultValue = "1") int page
            , @RequestParam(defaultValue = "3") int size
            , @RequestParam int groupId) {
        try {
            List<Staff> list = new ArrayList<>();
            Pageable pageable = PageRequest.of(page - 1, size);
            Page<Staff> staffPage;
            staffPage = staffService.getListStaffByGroup(groupId, pageable);
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
            Map<String, Object> response = new HashMap<>();
            response.put("exception", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/addGroup")
    public ResponseEntity<?> addGroup(@RequestBody GroupUpdateDTO dto) {
        try {
            groupService.addGroup(dto);
            return new ResponseEntity<>("Add group" + dto.getGroupName() + " successfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }

    @PutMapping("/editGroup/{id}")
    public ResponseEntity<?> editGroup(@PathVariable("id") int id, @RequestBody GroupUpdateDTO dto) {
        try {
            groupService.editGroup(id, dto);
            return new ResponseEntity<>("Update group successfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }

    @DeleteMapping("/deleteGroup/{id}")
    public ResponseEntity<?> deleteGroup(@PathVariable("id") int id) throws NotFoundException {
        try {
            groupService.deleteGroup(id);
            return new ResponseEntity<>("Delete successfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
