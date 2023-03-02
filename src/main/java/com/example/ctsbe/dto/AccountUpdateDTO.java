package com.example.ctsbe.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import java.time.Instant;
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AccountUpdateDTO {
    private Integer id;
    @NotBlank(message = "Username is required")
    private String username;
    @NotBlank(message = "Password is required")
    private String password;
}
