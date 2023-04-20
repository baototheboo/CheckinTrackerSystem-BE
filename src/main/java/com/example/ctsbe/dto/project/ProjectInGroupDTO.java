package com.example.ctsbe.dto.project;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjectInGroupDTO {
    private Integer projectId;
    private String projectName;
    private String status;
}
