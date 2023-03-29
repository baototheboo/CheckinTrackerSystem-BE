package com.example.ctsbe.dto.account;

import com.example.ctsbe.exception.PasswordMatches;
import lombok.*;

import javax.validation.constraints.NotBlank;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class AccountUpdateDTO {
    @NotBlank(message = "Password is required")
    private String password;

    @NotBlank(message = "Password is required")
    private String newPassword;

    @NotBlank(message = "Password is required")
    private String confirmNewPassword;
}
