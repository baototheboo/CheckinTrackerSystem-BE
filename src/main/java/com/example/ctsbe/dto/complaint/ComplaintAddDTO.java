package com.example.ctsbe.dto.complaint;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ComplaintAddDTO {
    @NotBlank(message = "Nội dung không được để trống")
    private String content;
    private Integer complaintTypeId;
}
