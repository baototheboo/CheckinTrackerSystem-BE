package com.example.ctsbe.dto.account;

import com.example.ctsbe.constant.ApplicationConstant;
import com.example.ctsbe.dto.staff.StaffAddDTO;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
public class AccountAddDTO {
    @NotBlank(message = "Tên đăng nhập không được để trống")
    private String username;
    @NotBlank(message = "Mật khẩu không được để trống")
    //@Length(min = 6,message = "Mật khẩu phải có ít nhất 6 kí tự")
    @Pattern(regexp = ApplicationConstant.PASSWORD_REGEX,message =
            "Mật khẩu phải bao gồm 1 kí tự viết hoa, " +
            "một kí tự viết thường, " +
            "một chữ số, "+
            "một kí tự đặc biệt.")
    private String password;

    private Integer roleId;

    @Valid
    private StaffAddDTO staffAddDTO;


}
