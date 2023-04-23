package com.example.ctsbe.dto.staff;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StaffDTO {
    private int id;
    private String username;
    private String email;
    private String fullName;
    private String dateOfBirth;
    private String phone;
    private String promotionLevel;
    private Integer promotionLevelId;
    private String roleName;
    private Integer roleId;
    private boolean enable;
}
