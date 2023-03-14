package com.example.ctsbe.service;

import com.example.ctsbe.dto.staffProject.StaffProjectAddDTO;
import com.example.ctsbe.dto.staffProject.StaffProjectDTO;
import com.example.ctsbe.entity.Project;
import com.example.ctsbe.entity.StaffProject;
import com.example.ctsbe.entity.StaffProjectId;
import com.example.ctsbe.repository.ProjectRepository;
import com.example.ctsbe.repository.StaffProjectRepository;
import com.example.ctsbe.repository.StaffRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class StaffProjectServiceImpl implements StaffProjectService{
    @Autowired
    private StaffProjectRepository repository;
    @Autowired
    private StaffRepository staffRepository;
    @Autowired
    private ProjectRepository projectRepository;
    @Override
    public StaffProjectDTO addStaffToProject(StaffProjectAddDTO dto) {
        StaffProject staffProject = new StaffProject();
        StaffProjectId id = new StaffProjectId();
        id.setProjectId(dto.getProjectId());
        id.setStaffId(dto.getStaffId());
        staffProject.setId(id);
        staffProject.setStaff(staffRepository.getById(dto.getStaffId()));
        staffProject.setProject(projectRepository.getById(dto.getProjectId()));
        repository.save(staffProject);
        StaffProjectDTO staffProjectDTO = new StaffProjectDTO();
        staffProjectDTO.setStaffName(staffProject.getStaff().getFirstName());
        staffProjectDTO.setProjectName(staffProject.getProject().getProjectName());
        return staffProjectDTO;
    }

    @Override
    public void removeStaffFromProject(StaffProjectAddDTO dto) {
        StaffProjectId id = new StaffProjectId();
        id.setStaffId(dto.getStaffId());
        id.setProjectId(dto.getProjectId());
        StaffProject existedSP = repository.getById(id);
        repository.delete(existedSP);
    }

    @Override
    public Page<StaffProject> getAllStaffInProject(Project project, Pageable pageable) {
        return repository.findByProject(project, pageable);
    }
}
