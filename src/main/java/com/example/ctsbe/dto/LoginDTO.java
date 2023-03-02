package com.example.ctsbe.dto;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

public class LoginDTO {
    @NotNull(message = "Hãy nhập tên đăng nhập")
    //@Length(min = 5, max = 50)
    private String username;

    @NotNull(message = "Hãy nhập mật khẩu")
    //@Length(min = 5, max = 10)
    private String password;

    public LoginDTO() {
    }

    public LoginDTO(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
