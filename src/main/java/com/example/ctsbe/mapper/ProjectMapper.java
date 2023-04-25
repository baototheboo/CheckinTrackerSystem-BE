package com.example.ctsbe.mapper;

import com.example.ctsbe.dto.project.ProjectDTO;
import com.example.ctsbe.dto.project.ProjectInGroupDTO;
import com.example.ctsbe.dto.project.ProjectInProfileDTO;
import com.example.ctsbe.entity.Project;
import com.example.ctsbe.repository.StaffProjectRepository;
import com.example.ctsbe.service.StaffProjectService;
import com.example.ctsbe.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProjectMapper {
    @Autowired
    private static StaffProjectService staffProjectService;
    public static ProjectDTO convertEntityToDto(Project project){
        ProjectDTO dto = new ProjectDTO(
                project.getId(),
                project.getProjectName(),
                project.getProjectManager().getFullName(),
                project.getProjectManager().getId(),
                project.getGroup().getGroupName(),
                project.getGroup().getId(),
                project.getStatus()
        );
        return dto;
    }

    public static ProjectInProfileDTO convertProjectToProjectProfileDto(Project project){
        DateUtil dateUtil = new DateUtil();
        ProjectInProfileDTO dto = new ProjectInProfileDTO(
                project.getProjectName(),
                dateUtil.convertInstantToStringYearMonthDay(project.getCreatedDate()),
                project.getStatus()
        );
        return dto;
    }

    public static ProjectInGroupDTO convertProjectToProjectInGroupDto(Project project){
        ProjectInGroupDTO dto = new ProjectInGroupDTO(
                project.getId(),
                project.getProjectName(),
                project.getStatus()
        );
        return dto;
    }
}
