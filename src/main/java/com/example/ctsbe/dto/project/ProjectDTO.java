package com.example.ctsbe.dto.project;

import com.example.ctsbe.dto.staff.StaffDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjectDTO {
    private String projectName;
    private String projectManagerName;
    private String group;
    //private List<StaffDTO> listStaff;
}