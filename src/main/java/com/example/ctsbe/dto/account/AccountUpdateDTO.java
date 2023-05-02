package com.example.ctsbe.dto.account;

import com.example.ctsbe.constant.ApplicationConstant;
import com.example.ctsbe.exception.PasswordMatches;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AccountUpdateDTO {
    @NotBlank(message = "Vui lòng nhập mật khẩu hiện tại")
    private String password;

    @NotBlank(message = "Vui lòng nhập mật khẩu mới")
    @Pattern(regexp = ApplicationConstant.PASSWORD_REGEX,message = "Mật khẩu phải có 6 đến 20 kí tự bao gồm 1 kí tự viết hoa, " +
                                                                    "một kí tự viết thường, " +
                                                                    "một chữ số, "+
                                                                    "một kí tự đặc biệt.")
    private String newPassword;

    @NotBlank(message = "Vui lòng nhập lại mật khẩu mới")
    private String confirmNewPassword;
}
