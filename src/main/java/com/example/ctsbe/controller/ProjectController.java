package com.example.ctsbe.controller;

import com.example.ctsbe.dto.project.ProjectDTO;
import com.example.ctsbe.dto.staffProject.StaffInProjectDTO;
import com.example.ctsbe.dto.staffProject.StaffProjectAddDTO;
import com.example.ctsbe.dto.staffProject.StaffProjectDTO;
import com.example.ctsbe.entity.Project;
import com.example.ctsbe.entity.StaffProject;
import com.example.ctsbe.mapper.ProjectMapper;
import com.example.ctsbe.mapper.StaffProjectMapper;
import com.example.ctsbe.service.ProjectService;
import com.example.ctsbe.service.StaffProjectService;
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
@RequestMapping("/projects")
@CrossOrigin(origins = "*")
public class ProjectController {
    @Autowired
    private ProjectService projectService;

    @Autowired
    private StaffProjectService staffProjectService;

    @GetMapping("/getAllProject")
    public ResponseEntity<Map<String, Object>> getAllProject(@RequestParam(defaultValue = "1") int page
            , @RequestParam(defaultValue = "3") int size
            , @RequestParam(required = false) String name) {
        try {
            List<Project> list = new ArrayList<>();
            Pageable pageable = PageRequest.of(page - 1, size);
            Page<Project> projectPage;
            if (name == null) {
                projectPage = projectService.getAllProject(pageable);
            } else {
                projectPage = projectService.getProjectByNameContain(name,pageable);
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

    @PostMapping("/addStaffToProject")
    public ResponseEntity<?> addStaffToProject(@RequestBody StaffProjectAddDTO dto) {
        try {
            StaffProjectDTO staffProjectDTO = staffProjectService.addStaffToProject(dto);
            return new ResponseEntity<>("Add staff "+staffProjectDTO.getStaffName()
                    +" to "+staffProjectDTO.getProjectName() +" successfully", HttpStatus.OK);
        }catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/removeStaffFromProject")
    public ResponseEntity<?> removeStaffFromProject(@RequestBody StaffProjectAddDTO dto) {
        try {
            staffProjectService.removeStaffFromProject(dto);
            return new ResponseEntity<>("Remove successfully", HttpStatus.OK);
        }catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getAllStaffInProject")
    public ResponseEntity<Map<String, Object>> getAllProject(@RequestParam(defaultValue = "1") int page
            , @RequestParam(defaultValue = "3") int size
            , @RequestParam int projectId) {
        try{
            List<StaffProject> list = new ArrayList<>();
            Pageable pageable = PageRequest.of(page - 1, size);
            Project project = projectService.getProjectById(projectId);
            Page<StaffProject> projectPage = staffProjectService.getAllStaffInProject(project,pageable);
            list = projectPage.getContent();
            List<StaffInProjectDTO> listDto = list.stream().
                    map(StaffProjectMapper::convertEntityToDto).collect(Collectors.toList());
            Map<String, Object> response = new HashMap<>();
            response.put("list", listDto);
            response.put("currentPage", projectPage.getNumber());
            response.put("allProducts", projectPage.getTotalElements());
            response.put("allPages", projectPage.getTotalPages());
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
