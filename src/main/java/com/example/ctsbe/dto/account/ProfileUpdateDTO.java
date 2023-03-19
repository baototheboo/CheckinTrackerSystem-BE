package com.example.ctsbe.dto.account;

import lombok.Data;

@Data
public class ProfileUpdateDTO {
    private String username;
    private String firstName;
    private String surname;
    private String email;
    private String dateOfBirth;
    private String phone;
}
