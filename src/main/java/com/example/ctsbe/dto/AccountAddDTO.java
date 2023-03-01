package com.example.ctsbe.dto;

import lombok.Data;

import java.time.Instant;

@Data
public class AccountAddDTO {
    private String username;
    private String password;
    private String email;
    private String firstName;
    private String surname;
    private Instant createdDate;
    private String phone;
    private Integer roleId;
}
