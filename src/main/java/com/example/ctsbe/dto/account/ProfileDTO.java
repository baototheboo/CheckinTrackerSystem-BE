package com.example.ctsbe.dto.account;

import lombok.Data;

@Data
public class ProfileDTO {
    private String username;
    private String fullName;
    private String email;
    private String dateOfBirth;
    private String phone;
    private String roleName;
    private Integer promotionLevel;

}
