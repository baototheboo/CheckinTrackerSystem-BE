package com.example.ctsbe.mapper;

import com.example.ctsbe.dto.project.ProjectDTO;
import com.example.ctsbe.dto.project.ProjectInProfileDTO;
import com.example.ctsbe.entity.Project;
import com.example.ctsbe.util.DateUtil;
import org.springframework.stereotype.Component;

@Component
public class ProjectMapper {
    public static ProjectDTO convertEntityToDto(Project project){
        ProjectDTO dto = new ProjectDTO(
                project.getId(),
                project.getProjectName(),
                project.getProjectManager().getSurname() + " "
                        + project.getProjectManager().getFirstName(),
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
}
