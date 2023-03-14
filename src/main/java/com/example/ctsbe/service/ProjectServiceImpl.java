package com.example.ctsbe.service;

import com.example.ctsbe.dto.project.ProjectAddDTO;
import com.example.ctsbe.entity.Project;
import com.example.ctsbe.repository.GroupRepository;
import com.example.ctsbe.repository.ProjectRepository;
import com.example.ctsbe.repository.StaffRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class ProjectServiceImpl implements ProjectService{
    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private GroupRepository groupRepository;
    @Autowired
    private StaffRepository staffRepository;
    @Override
    public Page<Project> getAllProject(Pageable pageable) {
        return projectRepository.findAll(pageable);
    }

    @Override
    public Project addProject(ProjectAddDTO dto) {
        Project project = new Project();
        project.setProjectName(dto.getProjectName());
        project.setProjectManager(staffRepository.getById(dto.getProjectManagerId()));
        project.setGroup(groupRepository.getById(dto.getGroupId()));
        project.setCreatedDate(Instant.now());
        project.setLastUpdated(Instant.now());
        project.setStatus(dto.getStatus());
        projectRepository.save(project);
        return project;
    }

    @Override
    public Page<Project> getProjectByNameContain(String name, Pageable pageable) {
        return projectRepository.findByProjectNameContaining(name, pageable);
    }

    @Override
    public Project getProjectById(int id) {
        return projectRepository.getById(id);
    }

    @Override
    public void editProject(int id, ProjectAddDTO dto) {
        Project project = projectRepository.getById(id);
        project.setProjectName(dto.getProjectName());
        project.setProjectManager(staffRepository.getById(dto.getProjectManagerId()));
        project.setGroup(groupRepository.getById(dto.getGroupId()));
        project.setLastUpdated(Instant.now());
        project.setStatus(dto.getStatus());
        projectRepository.save(project);
    }

}
