package com.example.ctsbe.dto;

import com.example.ctsbe.entity.Role;
import com.example.ctsbe.entity.Staff;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.time.Instant;

@Data

public class AccountDTO {
    @NotBlank(message = "Username is required")
    private String username;
    private String roleName;
    private String staffName;
    private Instant lastLogin;
    private Byte enable;
}
