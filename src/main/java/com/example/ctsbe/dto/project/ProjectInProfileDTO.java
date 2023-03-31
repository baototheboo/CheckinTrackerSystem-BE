package com.example.ctsbe.dto.project;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjectInProfileDTO {
    private String projectName;
    private String createDate;
    private String status;
}
