package com.example.ctsbe.dto;

import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.time.Instant;

@Data
public class AccountAddDTO {
    @NotBlank(message = "Tên đăng nhập không được để trống")
    private String username;
    @NotBlank(message = "Mật khẩu không được để trống")
    private String password;

    private Integer roleId;

    @Valid
    private StaffDTO staffDTO;


}
