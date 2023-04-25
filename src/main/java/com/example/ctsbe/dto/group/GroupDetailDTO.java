package com.example.ctsbe.dto.group;

import com.example.ctsbe.dto.project.ProjectInGroupDTO;
import com.example.ctsbe.dto.project.ProjectInProfileDTO;
import com.example.ctsbe.dto.staff.StaffDTO;
import com.example.ctsbe.entity.Staff;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GroupDetailDTO {
    private Map<String, Object> response;
    private List<ProjectInGroupDTO> listProject;
}
