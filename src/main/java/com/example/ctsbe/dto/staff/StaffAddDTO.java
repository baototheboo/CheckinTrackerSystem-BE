package com.example.ctsbe.dto.staff;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
public class StaffAddDTO {
    @NotBlank(message = "Email không được để trống")
    @Email(message = "Email không hợp lệ, email phải được để dưới dạng @gmail.com")
    private String email;
    @NotBlank(message = "First name is required")
    private String firstName;
    @NotBlank(message = "Sur name is required")
    private String surname;
    private String dateOfBirth;
    @NotBlank(message = "Phone is required")
    private String phone;
    //private Integer promotionLevelId;

}
