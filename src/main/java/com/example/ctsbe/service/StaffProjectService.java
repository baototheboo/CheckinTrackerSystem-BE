package com.example.ctsbe.service;

import com.example.ctsbe.dto.staffProject.StaffProjectAddDTO;
import com.example.ctsbe.dto.staffProject.StaffProjectDTO;
import com.example.ctsbe.entity.Project;
import com.example.ctsbe.entity.StaffProject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface StaffProjectService {
    void addStaffToProject(StaffProjectAddDTO dto);

    void removeStaffFromProject(StaffProjectAddDTO dto);

    Page<StaffProject> getAllStaffInProject(Project project, Pageable pageable);
}
