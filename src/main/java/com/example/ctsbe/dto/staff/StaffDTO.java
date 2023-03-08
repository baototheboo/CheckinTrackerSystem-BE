package com.example.ctsbe.dto.staff;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
public class StaffDTO {
    private int id;
    private String email;
    private String fullName;
    private String dateOfBirth;
    private String phone;
    private String promotionLevel;
}
