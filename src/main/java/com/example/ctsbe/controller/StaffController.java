package com.example.ctsbe.controller;

import com.example.ctsbe.dto.image.ImageSetupDTO;
import com.example.ctsbe.dto.staff.StaffAddDTO;
import com.example.ctsbe.dto.staff.StaffAvailableDTO;
import com.example.ctsbe.dto.staff.StaffDTO;
import com.example.ctsbe.dto.staff.StaffUpdateDTO;
import com.example.ctsbe.entity.Account;
import com.example.ctsbe.entity.Group;
import com.example.ctsbe.entity.Staff;
import com.example.ctsbe.exception.ExceptionObject;
import com.example.ctsbe.mapper.StaffMapper;
import com.example.ctsbe.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
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

    @Autowired
    private StaffProjectService staffProjectService;

    @Autowired
    private GroupService groupService;

    @Autowired
    private ImageSetupService imageSetupService;


    @PutMapping("/changePromotionLevel")
    @RolesAllowed("ROLE_HUMAN RESOURCE")
    public ResponseEntity<?> changePromotionLevel(@RequestBody StaffUpdateDTO dto) {
        try {
            ExceptionObject exceptionObject = new ExceptionObject();
            Map<String, String> errorMap = new HashMap<>();
            int errorCode = HttpStatus.INTERNAL_SERVER_ERROR.value();
            exceptionObject.setCode(errorCode);
            Staff staff = staffService.getStaffById(dto.getStaffId());
            //Account account = accountService.getAccountById(dto.getStaffId());
            Group group = (staff.getGroup() == null) ? null : groupService.findById(staff.getGroup().getId());
            if (dto.getRoleId() == 4) {
                if(group == null){
                    staffService.changePromotionLevel(dto);
                    return new ResponseEntity<>("Cập nhật nhân viên với id " + dto.getStaffId() + " thành công", HttpStatus.OK);
                }
                else if (group.getGroupLeader().getId() == dto.getStaffId()) {
                    staffService.changePromotionLevel(dto);
                    return new ResponseEntity<>("Cập nhật nhân viên với id " + dto.getStaffId() + " thành công", HttpStatus.OK);
                } else {
                    errorMap.put("exception", "Nhóm của người này hiện đã có Group Leader.");
                    exceptionObject.setError(errorMap);
                    return new ResponseEntity<>(exceptionObject, HttpStatus.BAD_REQUEST);
                }
            }
            /*else if(dto.getRoleId() == 3){
                if(staffProjectService.checkStaffInProjectHavePM(dto.getStaffId()) == true){
                    staffService.changePromotionLevel(dto);
                    return new ResponseEntity<>("Cập nhật nhân viên với id " + dto.getStaffId() + " thành công", HttpStatus.OK);
                }else {
                    errorMap.put("exception", "Dự án này hiện đã có Project Manager.");
                    exceptionObject.setError(errorMap);
                    return new ResponseEntity<>(exceptionObject, HttpStatus.BAD_REQUEST);
                }
            }*/
            staffService.changePromotionLevel(dto);
            return new ResponseEntity<>("Cập nhật nhân viên với id " + dto.getStaffId() + " thành công", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/getAllStaff")
    @RolesAllowed("ROLE_HUMAN RESOURCE")
    public ResponseEntity<Map<String, Object>> getAllStaff(@RequestParam(defaultValue = "1") int page
            , @RequestParam(defaultValue = "3") int size
            , @RequestParam(required = false) String name
            , @RequestParam(defaultValue = "2") int enable) {
        try {
            List<Staff> list = new ArrayList<>();
            Pageable pageable = PageRequest.of(page - 1, size);
            Page<Staff> staffPage;
            if (name == null) {
                if (enable == 2) {
                    staffPage = staffService.getAllStaff(pageable);
                } else {
                    staffPage = staffService.getListStaffByEnable((byte) enable, pageable);
                }
            } else {
                if (enable == 2) {
                    staffPage = staffService.getStaffByName(name, pageable);
                } else {
                    staffPage = staffService.getListStaffByNameAndEnable(name, (byte) enable, pageable);
                }
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
            Map<String, Object> response = new HashMap<>();
            response.put("exception", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getListStaffForTimeSheet")
    //@RolesAllowed({"ROLE_HUMAN RESOURCE","ROLE_PROJECT MANAGER","ROLE_GROUP LEADER"})
    public ResponseEntity<?> getListStaffForTimeSheet(@RequestParam int staffId
            , @RequestParam(defaultValue = "0") int projectId
    ) {
        try {
            List<Staff> list = staffService.getListStaffForTimeSheet(staffId, projectId);
            List<StaffAvailableDTO> listDto = list.stream().
                    map(StaffMapper::convertStaffToStaffAvailableDto).collect(Collectors.toList());
            return new ResponseEntity<>(listDto, HttpStatus.OK);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("exception", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getAvailableStaff/{groupId}")
    public ResponseEntity<Map<String, Object>> getAvailableStaff(@PathVariable("groupId") int groupId, @RequestParam int projectId) {
        try {
            List<Staff> list = staffService.getListAvailableStaff(groupId, projectId);
            List<StaffAvailableDTO> listDto = list.stream().
                    map(StaffMapper::convertStaffToStaffAvailableDto).collect(Collectors.toList());
            Map<String, Object> response = new HashMap<>();
            response.put("list", listDto);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("exception", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/getListStaffAvailableAddToGroup")
    public ResponseEntity<Map<String, Object>> getListStaffAvailableAddToGroup() {
        try {
            List<Staff> list = staffService.getListStaffAddToGroup();
            List<StaffAvailableDTO> listDto = list.stream().
                    map(StaffMapper::convertStaffToStaffAvailableDto).collect(Collectors.toList());
            Map<String, Object> response = new HashMap<>();
            response.put("list", listDto);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("exception", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/getStaffByRole")
    public ResponseEntity<Map<String, Object>> getStaffByRole(@RequestParam int role) {
        try {
            List<Staff> list = staffService.getStaffsByRole(role);
            List<StaffAvailableDTO> listDto = list.stream().
                    map(StaffMapper::convertStaffToStaffAvailableDto).collect(Collectors.toList());
            Map<String, Object> response = new HashMap<>();
            response.put("list", listDto);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("exception", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/getListPMAvailable")
    public ResponseEntity<Map<String, Object>> getListPMAvailable() {
        try {
            List<Staff> list = staffService.getListPMAvailable();
            List<StaffAvailableDTO> listDto = list.stream().
                    map(StaffMapper::convertStaffToStaffAvailableDto).collect(Collectors.toList());
            Map<String, Object> response = new HashMap<>();
            response.put("list", listDto);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("exception", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/getListGLAvailable")
    public ResponseEntity<Map<String, Object>> getListGLAvailable() {
        try {
            List<Staff> list = staffService.getListGLAvailable();
            List<StaffAvailableDTO> listDto = list.stream().
                    map(StaffMapper::convertStaffToStaffAvailableDto).collect(Collectors.toList());
            Map<String, Object> response = new HashMap<>();
            response.put("list", listDto);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("exception", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{staffId}/get-image-setup")
    public ResponseEntity<Page<ImageSetupDTO>> getImageSetup(@PathVariable Integer staffId,
                                                             @RequestParam(defaultValue = "1") int page,
                                                             @RequestParam(defaultValue = "3") int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<ImageSetupDTO> result = imageSetupService.findImageSetup(staffId, pageable);
        Map<String, Object> response = new HashMap<>();
        response.put("listImage", result);
        response.put("currentPage", result.getNumber());
        response.put("allProducts", result.getTotalElements());
        response.put("allPages", result.getTotalPages());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

}
