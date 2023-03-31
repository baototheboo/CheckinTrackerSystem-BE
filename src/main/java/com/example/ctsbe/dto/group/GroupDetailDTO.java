package com.example.ctsbe.dto.group;

import com.example.ctsbe.dto.project.ProjectInProfileDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GroupDetailDTO {
    private Integer id;
    private String groupName;
    private String groupLeaderName;
    private List<ProjectInProfileDTO> listProject;
}
