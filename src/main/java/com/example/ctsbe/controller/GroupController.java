package com.example.ctsbe.controller;

import com.example.ctsbe.dto.group.*;
import com.example.ctsbe.dto.project.ProjectInGroupDTO;
import com.example.ctsbe.dto.project.ProjectInProfileDTO;
import com.example.ctsbe.dto.staff.StaffAvailableDTO;
import com.example.ctsbe.dto.staff.StaffDTO;
import com.example.ctsbe.dto.staffProject.StaffProjectAddDTO;
import com.example.ctsbe.entity.Account;
import com.example.ctsbe.entity.Group;
import com.example.ctsbe.entity.Project;
import com.example.ctsbe.entity.Staff;
import com.example.ctsbe.exception.ExceptionObject;
import com.example.ctsbe.mapper.GroupMapper;
import com.example.ctsbe.mapper.ProjectMapper;
import com.example.ctsbe.mapper.StaffMapper;
import com.example.ctsbe.service.AccountService;
import com.example.ctsbe.service.GroupService;
import com.example.ctsbe.service.ProjectService;
import com.example.ctsbe.service.StaffService;
import com.example.ctsbe.util.JwtTokenUtil;
import javassist.NotFoundException;
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
    @Autowired
    private ProjectService projectService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @GetMapping("/getAllGroups")
    public ResponseEntity<Map<String, Object>> getAllGroups(@RequestParam(defaultValue = "1") int page
            , @RequestParam(defaultValue = "3") int size
            , @RequestParam(defaultValue = "0") int staffId
            , @RequestParam(required = false) String groupName) {
        try {
            List<Group> list = new ArrayList<>();
            Pageable pageable = PageRequest.of(page - 1, size);
            Page<Group> groupPage;
            if (groupName == null) {
                if(staffId == 0){
                    groupPage = groupService.getAllGroup(pageable);
                }else {
                    groupPage = groupService.getListGroupByStaffId(staffId,pageable);
                }
            } else {
                if(staffId == 0){
                    groupPage = groupService.getAllGroupByName(groupName, pageable);
                }else {
                    groupPage = groupService.getListGroupByStaffIdAndGroupName(staffId,groupName,pageable);
                }
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
    public ResponseEntity<?> getAllStaffInGroup(@RequestParam(defaultValue = "1") int page
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
            List<Project> projectList = projectService.getListProjectByGroupId(groupId);
            List<ProjectInGroupDTO> listPrjDto = projectList.stream().
                    map(ProjectMapper::convertProjectToProjectInGroupDto).collect(Collectors.toList());
            Map<String, Object> response = new HashMap<>();
            response.put("list", listDto);
            response.put("currentPage", staffPage.getNumber());
            response.put("allProducts", staffPage.getTotalElements());
            response.put("allPages", staffPage.getTotalPages());
            GroupDetailDTO dto = new GroupDetailDTO();
            dto.setResponse(response);
            dto.setListProject(listPrjDto);
            return new ResponseEntity<>(dto, HttpStatus.OK);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("exception", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getGroupDetails/{id}")
    public ResponseEntity<?> getGroupDetail(@PathVariable("id") int id) {
        try{
            Group group = groupService.findById(id);
            //GroupDetailDTO dto = GroupMapper.convertGroupToGroupDetailDTO(group);
            GroupDetailDTO dto = new GroupDetailDTO();
            List<Project> projectList = projectService.getListProjectByGroupId(group.getId());
            List<ProjectInGroupDTO> listDto = projectList.stream().
                    map(ProjectMapper::convertProjectToProjectInGroupDto).collect(Collectors.toList());
            dto.setListProject(listDto);
            return new ResponseEntity<>(dto,HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/addGroup")
    public ResponseEntity<?> addGroup(@RequestBody GroupAddDTO dto) {
        try {
            Group group = groupService.addGroup(dto);
            groupService.addGLToGroup(dto.getGroupLeaderId(), group.getId());
            return new ResponseEntity<>("Add group" + dto.getGroupName() + " successfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }

    @PutMapping("/editGroup/{id}")
    public ResponseEntity<?> editGroup(@PathVariable("id") int id, @RequestBody GroupUpdateDTO dto) {
        try {
            groupService.editGroup(id, dto);
            return new ResponseEntity<>("Cập nhật nhóm thành công", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }

    @DeleteMapping("/deleteGroup/{id}")
    public ResponseEntity<?> deleteGroup(@PathVariable("id") int id){
        try {
            groupService.deleteGroup(id);
            return new ResponseEntity<>("Delete successfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/addStaffToGroup")
    @RolesAllowed({"ROLE_GROUP LEADER","ROLE_HUMAN RESOURCE"})
    public ResponseEntity<?> addStaffToGroup(@RequestBody StaffProjectAddDTO dto) {
        try {
            int tokenId = getIdFromToken();
            Account tokenAccount = accountService.getAccountById(tokenId);
            Staff tokenStaff = tokenAccount.getStaff();
            ExceptionObject exceptionObject = new ExceptionObject();
            Map<String, String> errorMap = new HashMap<>();
            int errorCode = HttpStatus.INTERNAL_SERVER_ERROR.value();
            exceptionObject.setCode(errorCode);
            if(tokenAccount.getRole().getId() != 2){
                if(tokenStaff.getGroup().getId() != dto.getProjectId()){
                    errorMap.put("exception", "Bạn không có quyền thêm nhân viên vào nhóm!");
                    exceptionObject.setError(errorMap);
                    return new ResponseEntity<>(exceptionObject, HttpStatus.FORBIDDEN);
                }else {
                    groupService.addStaffToGroup(dto);
                    return new ResponseEntity<>("Thêm nhân viên vào nhóm thành công", HttpStatus.OK);
                }
            }else {
                groupService.addStaffToGroup(dto);
                return new ResponseEntity<>("Thêm nhân viên vào nhóm thành công", HttpStatus.OK);
            }
        }catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/removeStaffFromGroup")
    @RolesAllowed({"ROLE_GROUP LEADER","ROLE_HUMAN RESOURCE"})
    public ResponseEntity<?> removeStaffFromGroup(@RequestBody GroupRemoveStaffDTO dto) {
        try {
            int tokenId = getIdFromToken();

            Account tokenAccount = accountService.getAccountById(tokenId);
            Staff tokenStaff = tokenAccount.getStaff();
            Staff staff = staffService.getStaffById(dto.getStaffId().get(0));
            ExceptionObject exceptionObject = new ExceptionObject();
            Map<String, String> errorMap = new HashMap<>();
            int errorCode = HttpStatus.INTERNAL_SERVER_ERROR.value();
            exceptionObject.setCode(errorCode);
            if(tokenAccount.getRole().getId() == 4){
                if(tokenStaff.getGroup().getId() != staff.getGroup().getId()){
                    errorMap.put("exception", "Bạn không có quyền xóa nhân viên nhóm này!");
                    exceptionObject.setError(errorMap);
                    return new ResponseEntity<>(exceptionObject, HttpStatus.FORBIDDEN);
                }
                else if(staffService.checkStaffInRemoveFromGroup(dto)){
                    errorMap.put("exception", "Không thể xóa nhân viên khỏi nhóm vì nhân viên đang thực hiện dự án");
                    exceptionObject.setError(errorMap);
                    return new ResponseEntity<>(exceptionObject, HttpStatus.BAD_REQUEST);
                }
                else{
                    groupService.removeStaffFromGroup(dto);
                    return new ResponseEntity<>("Xóa nhân viên khỏi nhóm thành công", HttpStatus.OK);
                }
            }else {
                if(staffService.checkStaffInRemoveFromGroup(dto)){
                    errorMap.put("exception", "Không thể xóa nhân viên khỏi nhóm vì nhân viên đang thực hiện dự án");
                    exceptionObject.setError(errorMap);
                    return new ResponseEntity<>(exceptionObject, HttpStatus.BAD_REQUEST);
                }else {
                    groupService.removeStaffFromGroup(dto);
                    return new ResponseEntity<>("Xóa nhân viên khỏi nhóm thành công", HttpStatus.OK);
                }
            }
        }catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getListPMInGroup")
    public ResponseEntity<?> getListPMInGroup(@RequestParam int groupId){
        try{
            List<Staff> list= staffService.getListPMInGroup(groupId);
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

    @PutMapping("/setStaffToPM")
    public ResponseEntity<?> setStaffToPM(@RequestParam int staffId){
        try{
            staffService.setStaffToPM(staffId);
            return new ResponseEntity<>("Đưa nhân viên lên PM thành công",HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
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
