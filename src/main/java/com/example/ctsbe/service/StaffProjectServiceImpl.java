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

import java.util.List;

@Service
public class StaffProjectServiceImpl implements StaffProjectService {
    @Autowired
    private StaffProjectRepository repository;
    @Autowired
    private StaffRepository staffRepository;
    @Autowired
    private ProjectRepository projectRepository;

    @Override
    public void addStaffToProject(StaffProjectAddDTO dto) {
        StaffProject staffProject = new StaffProject();
        StaffProjectId id = new StaffProjectId();
        List<Integer> listStaffId = dto.getStaffId();
        for (int i = 0; i < listStaffId.size(); i++) {
            id.setProjectId(dto.getProjectId());
            id.setStaffId(listStaffId.get(i));
            staffProject.setId(id);
            staffProject.setStaff(staffRepository.getById(listStaffId.get(i)));
            staffProject.setProject(projectRepository.getById(dto.getProjectId()));
            repository.save(staffProject);
        }
    }

    @Override
    public void removeStaffFromProject(StaffProjectAddDTO dto) {
        StaffProjectId id = new StaffProjectId();
        List<Integer> listStaffId = dto.getStaffId();
        for (int i = 0; i < listStaffId.size(); i++) {
            id.setStaffId(listStaffId.get(i));
            id.setProjectId(dto.getProjectId());
            StaffProject existedSP = repository.getById(id);
            repository.delete(existedSP);
        }
    }

    @Override
    public Page<StaffProject> getAllStaffInProject(Project project, Pageable pageable) {
        return repository.findByProject(project, pageable);
    }
}
