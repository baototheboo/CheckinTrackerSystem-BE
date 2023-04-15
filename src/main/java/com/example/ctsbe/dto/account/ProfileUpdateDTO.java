package com.example.ctsbe.dto.account;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProfileUpdateDTO {
    private String firstName;
    private String surname;
    private String dateOfBirth;
    private String phone;
}
