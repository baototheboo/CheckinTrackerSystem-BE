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
    private String email;
    private String fullName;
    private String dateOfBirth;
    private String phone;
    private Integer promotionLevel;
    private String roleName;
}
