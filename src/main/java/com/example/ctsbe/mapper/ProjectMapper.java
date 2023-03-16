package com.example.ctsbe.mapper;

import com.example.ctsbe.dto.project.ProjectDTO;
import com.example.ctsbe.entity.Project;

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
}
