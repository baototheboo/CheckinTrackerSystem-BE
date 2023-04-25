package com.example.ctsbe.dto.project;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjectAddDTO {
    @NotBlank(message = "Tên dự án không được để trống!")
    private String projectName;
    @NotNull(message= "Hãy chọn người quản lý dự án!")
    private Integer projectManagerId;
    @NotNull(message = "Hãy chọn nhóm cho dự án!")
    private Integer groupId;
}
