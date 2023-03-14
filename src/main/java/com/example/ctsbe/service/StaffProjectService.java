package com.example.ctsbe.service;

import com.example.ctsbe.dto.staffProject.StaffProjectAddDTO;
import com.example.ctsbe.dto.staffProject.StaffProjectDTO;
import com.example.ctsbe.entity.Project;
import com.example.ctsbe.entity.StaffProject;
import com.example.ctsbe.entity.StaffProjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface StaffProjectService {
    StaffProjectDTO addStaffToProject(StaffProjectAddDTO dto);

    void removeStaffFromProject(StaffProjectAddDTO dto);

    Page<StaffProject> getAllStaffInProject(Project project, Pageable pageable);
}
