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
    @NotBlank(message = "Project name is required!")
    private String projectName;
    @NotNull(message= "Please choose the project manager!")
    private Integer projectManagerId;
    @NotNull(message = "Please choose the group!")
    private Integer groupId;
}
