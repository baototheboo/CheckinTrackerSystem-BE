package com.example.ctsbe.service;

import com.example.ctsbe.dto.staffProject.StaffProjectAddDTO;
import com.example.ctsbe.dto.staffProject.StaffProjectDTO;
import com.example.ctsbe.entity.Project;
import com.example.ctsbe.entity.Staff;
import com.example.ctsbe.entity.StaffProject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface StaffProjectService {
    void addPMToProject(int pmId,int prjId);

    void addStaffToProject(StaffProjectAddDTO dto);

    void removeStaffFromProject(StaffProjectAddDTO dto);

    Page<StaffProject> getAllStaffInProject(Project project, Pageable pageable);

    List<Project> getListProjectInProfile(int staffId);
    List<Staff> getListStaffByPrjId(int prjId);
}
