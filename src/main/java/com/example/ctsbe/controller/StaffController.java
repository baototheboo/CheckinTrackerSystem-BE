package com.example.ctsbe.controller;

import com.example.ctsbe.dto.image.ImageSetupDTO;
import com.example.ctsbe.dto.staff.StaffAddDTO;
import com.example.ctsbe.dto.staff.StaffAvailableDTO;
import com.example.ctsbe.dto.staff.StaffDTO;
import com.example.ctsbe.dto.staff.StaffUpdateDTO;
import com.example.ctsbe.entity.Staff;
import com.example.ctsbe.mapper.StaffMapper;
import com.example.ctsbe.service.AccountService;
import com.example.ctsbe.service.ImageSetupService;
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

    @Autowired
    private AccountService accountService;

    @Autowired
    private ImageSetupService imageSetupService;
    @PostMapping("/addStaff")
    public String addStaff(@RequestBody StaffAddDTO dto) {
        try {
            staffService.addStaff(dto);
            return "Add successfully";
        } catch (Exception e) {
            return e.getMessage();
        }

    }

    @PutMapping("/changePromotionLevel")
    //@RolesAllowed("ROLE_HUMAN RESOURCE")
    public ResponseEntity<?> changePromotionLevel(@RequestBody StaffUpdateDTO dto){
        try{
            staffService.changePromotionLevel(dto.getStaffId(), dto.getLevelId());
            return new ResponseEntity<>("Update promotion level of staff with id"+dto.getStaffId()+" successfully",HttpStatus.OK);
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
            Map<String, Object> response = new HashMap<>();
            response.put("exception",e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getAvailableStaff/{groupId}")
    public ResponseEntity<Map<String, Object>> getAvailableStaff(@PathVariable("groupId") int groupId){
        try{
            List<Staff> list= staffService.getListAvailableStaff(groupId);
            List<StaffAvailableDTO> listDto = list.stream().
                    map(StaffMapper::convertStaffToStaffAvailableDto).collect(Collectors.toList());
            Map<String, Object> response = new HashMap<>();
            response.put("list", listDto);
            return new ResponseEntity<>(response,HttpStatus.OK);
        }catch (Exception e){
            Map<String, Object> response = new HashMap<>();
            response.put("exception", e.getMessage());
            return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/getListStaffAvailableAddToGroup")
    public ResponseEntity<Map<String, Object>> getListStaffAvailableAddToGroup(){
        try{
            List<Staff> list= staffService.getListStaffAddToGroup();
            List<StaffAvailableDTO> listDto = list.stream().
                    map(StaffMapper::convertStaffToStaffAvailableDto).collect(Collectors.toList());
            Map<String, Object> response = new HashMap<>();
            response.put("list", listDto);
            return new ResponseEntity<>(response,HttpStatus.OK);
        }catch (Exception e){
            Map<String, Object> response = new HashMap<>();
            response.put("exception", e.getMessage());
            return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/getStaffByRole")
    public ResponseEntity<Map<String, Object>> getStaffByRole(@RequestParam int role){
        try{
            List<Staff> list= staffService.getStaffsByRole(role);
            List<StaffAvailableDTO> listDto = list.stream().
                    map(StaffMapper::convertStaffToStaffAvailableDto).collect(Collectors.toList());
            Map<String, Object> response = new HashMap<>();
            response.put("list", listDto);
            return new ResponseEntity<>(response,HttpStatus.OK);
        }catch (Exception e){
            Map<String, Object> response = new HashMap<>();
            response.put("exception", e.getMessage());
            return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/getListPMAvailable")
    public ResponseEntity<Map<String, Object>> getListPMAvailable(){
        try{
            List<Staff> list= staffService.getListPMAvailable();
            List<StaffAvailableDTO> listDto = list.stream().
                    map(StaffMapper::convertStaffToStaffAvailableDto).collect(Collectors.toList());
            Map<String, Object> response = new HashMap<>();
            response.put("list", listDto);
            return new ResponseEntity<>(response,HttpStatus.OK);
        }catch (Exception e){
            Map<String, Object> response = new HashMap<>();
            response.put("exception", e.getMessage());
            return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/getListGLAvailable")
    public ResponseEntity<Map<String, Object>> getListGLAvailable(){
        try{
            List<Staff> list= staffService.getListGLAvailable();
            List<StaffAvailableDTO> listDto = list.stream().
                    map(StaffMapper::convertStaffToStaffAvailableDto).collect(Collectors.toList());
            Map<String, Object> response = new HashMap<>();
            response.put("list", listDto);
            return new ResponseEntity<>(response,HttpStatus.OK);
        }catch (Exception e){
            Map<String, Object> response = new HashMap<>();
            response.put("exception", e.getMessage());
            return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
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
