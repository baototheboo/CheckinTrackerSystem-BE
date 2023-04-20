package com.example.ctsbe.dto.staff;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
public class StaffAddDTO {
    @NotBlank(message = "Email không được để trống")
    @Email(message = "Email không hợp lệ, email phải được để dưới dạng @gmail.com")
    private String email;
    @NotBlank(message = "Tên không được để trống")
    private String firstName;
    @NotBlank(message = "Họ và tên đệm không được để trống")
    private String surname;
    @NotBlank(message = "Vui lòng chọn ngày sinh")
    private String dateOfBirth;
    @NotBlank(message = "Số điện thoại không được để trống")
    @Length(min = 10,max = 10,message = "Số điện thoại phải bao gồm 10 chữ số")
    private String phone;
    //private Integer promotionLevelId;

}
