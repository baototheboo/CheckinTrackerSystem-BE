package com.example.ctsbe.service;

import com.example.ctsbe.dto.project.ProjectAddDTO;
import com.example.ctsbe.entity.Project;
import com.example.ctsbe.entity.StaffProject;
import com.example.ctsbe.entity.StaffProjectId;
import com.example.ctsbe.repository.GroupRepository;
import com.example.ctsbe.repository.ProjectRepository;
import com.example.ctsbe.repository.StaffProjectRepository;
import com.example.ctsbe.repository.StaffRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
public class ProjectServiceImpl implements ProjectService {
    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private GroupRepository groupRepository;
    @Autowired
    private StaffRepository staffRepository;
    @Autowired
    private StaffProjectRepository staffProjectRepository;
    @Autowired
    private StaffProjectService staffProjectService;

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
        project.setStatus("Processing");
        projectRepository.save(project);
        return project;
    }

    @Override
    public Page<Project> getListProjectByPMId(int staffId, Pageable pageable) {
        return projectRepository.getListProjectByPMId(staffId, pageable);
    }

    @Override
    public Page<Project> getListProjectByPMIdAndProjectName(int staffId, String name, Pageable pageable) {
        return projectRepository.getListProjectByPMIdAndProjectName(staffId, name, pageable);
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
        if(dto.getProjectManagerId() != project.getProjectManager().getId()){
            //delete old PM
            StaffProjectId staffProjectId = new StaffProjectId();
            staffProjectId.setProjectId(id);
            staffProjectId.setStaffId(project.getProjectManager().getId());
            StaffProject existedSP = staffProjectRepository.getById(staffProjectId);
            staffProjectRepository.delete(existedSP);
            //save new PM and Project to StaffProject
            project.setProjectManager(staffRepository.getById(dto.getProjectManagerId()));
            StaffProject newStaffProject = new StaffProject();
            StaffProjectId newId = new StaffProjectId();
            newId.setProjectId(id);
            newId.setStaffId(dto.getProjectManagerId());
            newStaffProject.setId(newId);
            newStaffProject.setStaff(staffRepository.getById(dto.getProjectManagerId()));
            newStaffProject.setProject(projectRepository.getById(id));
            staffProjectRepository.save(newStaffProject);
        }
        project.setGroup(groupRepository.getById(dto.getGroupId()));
        project.setLastUpdated(Instant.now());
        projectRepository.save(project);
    }

    @Override
    public void changeProjectStatus(int id, int status) {
        Project project = projectRepository.getById(id);
        if (status == 1) {
            project.setStatus("Processing");
        } else if (status == 2) {
            project.setStatus("Done");
        } else if (status == 3) {
            project.setStatus("Cancel");
        }
        project.setLastUpdated(Instant.now());
        projectRepository.save(project);
    }

    @Override
    public List<Project> getListProjectByGroupId(int groupId) {
        return projectRepository.getListProjectByGroupId(groupId);
    }

}
