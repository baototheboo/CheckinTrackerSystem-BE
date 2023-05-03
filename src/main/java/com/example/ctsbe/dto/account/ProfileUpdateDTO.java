package com.example.ctsbe.dto.account;

import com.example.ctsbe.constant.ApplicationConstant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProfileUpdateDTO {
    @NotBlank(message = "Vui lòng nhập tên!")
    private String firstName;
    @NotBlank(message = "Vui lòng nhập tên!")
    private String surname;
    private String dateOfBirth;
    @NotBlank(message = "Vui lòng nhập số điện thoại!")
    @Length(min = 10,max = 10,message = "Số điện thoại phải bao gồm 10 chữ số")
    @Pattern(regexp = ApplicationConstant.PHONE_REGEX,message = "Số điên thoại phải bao gồm các chữ số.")
    private String phone;
}
