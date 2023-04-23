package com.example.ctsbe.dto.staffProject;

import lombok.Data;

@Data
public class StaffInProjectDTO {
    private Integer staffId;
    private String username;
    private String fullName;
    private String email;
    private String phone;
    private String roleName;
    private Integer roleId;
    private Integer promotionLevel;

}
