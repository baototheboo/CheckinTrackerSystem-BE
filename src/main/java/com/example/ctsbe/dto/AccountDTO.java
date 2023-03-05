package com.example.ctsbe.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.time.Instant;

@Data

public class AccountDTO {
    private Integer id;
    @NotBlank(message = "Username is required")
    private String username;
    private String email;
    private String staffName;
    private String roleName;
    private String isEnable;
}
