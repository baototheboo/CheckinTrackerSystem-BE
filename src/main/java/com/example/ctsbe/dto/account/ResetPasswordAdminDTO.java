package com.example.ctsbe.dto.account;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResetPasswordAdminDTO {
    @NotBlank(message = "Mật khẩu không được để trống")
    private String newPassword;
}
