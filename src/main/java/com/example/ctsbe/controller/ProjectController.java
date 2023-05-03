package com.example.ctsbe.controller;

import com.example.ctsbe.dto.group.GroupUpdateDTO;
import com.example.ctsbe.dto.project.ProjectAddDTO;
import com.example.ctsbe.dto.project.ProjectDTO;
import com.example.ctsbe.dto.staffProject.StaffInProjectDTO;
import com.example.ctsbe.dto.staffProject.StaffProjectAddDTO;
import com.example.ctsbe.dto.staffProject.StaffProjectDTO;
import com.example.ctsbe.entity.Account;
import com.example.ctsbe.entity.Project;
import com.example.ctsbe.entity.Staff;
import com.example.ctsbe.entity.StaffProject;
import com.example.ctsbe.exception.ExceptionObject;
import com.example.ctsbe.mapper.ProjectMapper;
import com.example.ctsbe.mapper.StaffProjectMapper;
import com.example.ctsbe.service.AccountService;
import com.example.ctsbe.service.ProjectService;
import com.example.ctsbe.service.StaffProjectService;
import com.example.ctsbe.service.StaffService;
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
@RequestMapping("/projects")
@CrossOrigin(origins = "*")
public class ProjectController {
    @Autowired
    private ProjectService projectService;

    @Autowired
    private StaffProjectService staffProjectService;

    @Autowired
    private StaffService staffService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @GetMapping("/getAllProject")
    public ResponseEntity<Map<String, Object>> getAllProject(@RequestParam(defaultValue = "1") int page
            , @RequestParam(defaultValue = "3") int size
            , @RequestParam(defaultValue = "0") int staffId
            , @RequestParam(defaultValue = "0") int groupId
            , @RequestParam(required = false) String name) {
        try {
            List<Project> list = new ArrayList<>();
            Pageable pageable = PageRequest.of(page - 1, size);
            Page<Project> projectPage;
            if ((name == null) || (name.equals(""))) {
                if (staffId == 0) {
                    projectPage = projectService.getAllProject(groupId, pageable);
                } else {
                    projectPage = projectService.getListProjectByPMId(groupId, staffId, pageable);
                }
            } else {
                if (staffId == 0) {
                    projectPage = projectService.getProjectByNameContain(groupId, name, pageable);
                } else {
                    projectPage = projectService.getListProjectByPMIdAndProjectName(groupId, staffId, name, pageable);
                }
            }
            list = projectPage.getContent();
            List<ProjectDTO> listDto = list.stream().
                    map(ProjectMapper::convertEntityToDto).collect(Collectors.toList());
            Map<String, Object> response = new HashMap<>();
            response.put("list", listDto);
            response.put("currentPage", projectPage.getNumber());
            response.put("allProducts", projectPage.getTotalElements());
            response.put("allPages", projectPage.getTotalPages());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/addProject")
    @RolesAllowed({"ROLE_GROUP LEADER", "ROLE_PROJECT MANAGER"})
    public ResponseEntity<?> addProject(@Valid @RequestBody ProjectAddDTO dto) {
        try {
            int tokenId = getIdFromToken();
            Staff tokenStaff = staffService.getStaffById(tokenId);
            ExceptionObject exceptionObject = new ExceptionObject();
            Map<String, String> errorMap = new HashMap<>();
            int errorCode = HttpStatus.INTERNAL_SERVER_ERROR.value();
            exceptionObject.setCode(errorCode);
            if (tokenStaff.getGroup().getId() != dto.getGroupId()) {
                errorMap.put("exception", "Bạn không có quyền thêm dự án vào nhóm này!");
                exceptionObject.setError(errorMap);
                return new ResponseEntity<>(exceptionObject, HttpStatus.FORBIDDEN);
            } else if (projectService.findProjectByProjectName(dto.getProjectName()) != null) {
                errorMap.put("exception", "Dự án đã tồn tại!");
                exceptionObject.setError(errorMap);
                return new ResponseEntity<>(exceptionObject, HttpStatus.BAD_REQUEST);
            } else {
                Project p = projectService.addProject(dto);
                staffProjectService.addPMToProject(dto.getProjectManagerId(), p.getId());
                return new ResponseEntity<>("Tạo dự án " + dto.getProjectName() + " thành công.", HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/addPMToProject")
    public ResponseEntity<?> addPMToProject(@RequestParam int pmId, @RequestParam int projectId) {
        try {
            staffProjectService.addPMToProject(pmId, projectId);
            return new ResponseEntity<>("Add project PM successfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/editProject/{id}")
    @RolesAllowed({"ROLE_GROUP LEADER", "ROLE_PROJECT MANAGER"})
    public ResponseEntity<?> editProject(@PathVariable("id") int id, @RequestBody ProjectAddDTO dto) {
        try {
            int tokenId = getIdFromToken();
            Account account = accountService.getAccountById(tokenId);
            Staff tokenStaff = account.getStaff();
            Project project = projectService.getProjectById(id);
            ExceptionObject exceptionObject = new ExceptionObject();
            Map<String, String> errorMap = new HashMap<>();
            int errorCode = HttpStatus.INTERNAL_SERVER_ERROR.value();
            exceptionObject.setCode(errorCode);
            if (account.getRole().getId() == 4 && tokenStaff.getId() != project.getGroup().getGroupLeader().getId()) {
                errorMap.put("exception", "Bạn không có quyền sửa dự án vào nhóm này!");
                exceptionObject.setError(errorMap);
                return new ResponseEntity<>(exceptionObject, HttpStatus.FORBIDDEN);
            } else if (account.getRole().getId() == 3 && tokenStaff.getId() != project.getProjectManager().getId()) {
                errorMap.put("exception", "Bạn không có quyền sửa dự án vào nhóm này!");
                exceptionObject.setError(errorMap);
                return new ResponseEntity<>(exceptionObject, HttpStatus.FORBIDDEN);
            }
            else {
                projectService.editProject(id, dto);
                return new ResponseEntity<>("Cập nhật dự án thành công", HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/changeProjectStatus/{id}/{status}")
    public ResponseEntity<?> changeProjectStatus(@PathVariable("id") int id, @PathVariable("status") int status) {
        try {
            projectService.changeProjectStatus(id, status);
            return new ResponseEntity<>("Cập nhật dự án thành công", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/addStaffToProject")
    //@RolesAllowed("ROLE_PROJECT MANAGER")
    public ResponseEntity<?> addStaffToProject(@RequestBody StaffProjectAddDTO dto) {
        try {
            //int staffId = getIdFromToken();
            //int tokenRoleId = accountService.getAccountById(staffId).getRole().getId();
            if (dto.getStaffId().size() == 0) {
                return new ResponseEntity<>("Chưa chọn nhân viên.", HttpStatus.BAD_REQUEST);
            } else {
                staffProjectService.addStaffToProject(dto);
            }
            return new ResponseEntity<>("Thêm nhân viên vào dự án thành công", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/removeStaffFromProject")
    public ResponseEntity<?> removeStaffFromProject(@RequestBody StaffProjectAddDTO dto) {
        try {
            if (dto.getStaffId().size() == 0) {
                return new ResponseEntity<>("Chưa chọn nhân viên.", HttpStatus.BAD_REQUEST);
            } else {
                staffProjectService.removeStaffFromProject(dto);
            }
            return new ResponseEntity<>("Xóa nhân viên thành công", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getAllStaffInProject")
    public ResponseEntity<Map<String, Object>> getAllStaffInProject(@RequestParam(defaultValue = "1") int page
            , @RequestParam(defaultValue = "3") int size
            , @RequestParam int projectId) {
        try {
            List<StaffProject> list = new ArrayList<>();
            Pageable pageable = PageRequest.of(page - 1, size);
            Project project = projectService.getProjectById(projectId);
            Page<StaffProject> projectPage = staffProjectService.getAllStaffInProject(project, pageable);
            list = projectPage.getContent();
            List<StaffInProjectDTO> listDto = list.stream().
                    map(StaffProjectMapper::convertEntityToDto).collect(Collectors.toList());
            Map<String, Object> response = new HashMap<>();
            response.put("list", listDto);
            response.put("currentPage", projectPage.getNumber());
            response.put("allProducts", projectPage.getTotalElements());
            response.put("allPages", projectPage.getTotalPages());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
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
