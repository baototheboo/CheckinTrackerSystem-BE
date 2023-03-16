package com.example.ctsbe.service;

import com.example.ctsbe.dto.project.ProjectAddDTO;
import com.example.ctsbe.entity.Project;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProjectService {
    Page<Project> getAllProject(Pageable pageable);
    Project addProject(ProjectAddDTO dto);
    Page<Project> getProjectByNameContain(String name, Pageable pageable);
    Project getProjectById(int id);
    void editProject(int id,ProjectAddDTO dto);
    void changeProjectStatus(int id,int status);
}
