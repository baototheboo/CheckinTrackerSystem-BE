package com.example.ctsbe.dto.account;

import com.example.ctsbe.dto.project.ProjectInProfileDTO;
import lombok.Data;

import java.util.List;

@Data
public class ProfileDTO {
    private Integer id;
    private String username;
    private String firstName;
    private String surname;
    private String email;
    private String dateOfBirth;
    private String phone;
    private String roleName;
    private Integer promotionLevel;
    private List<ProjectInProfileDTO> listProject;
}
