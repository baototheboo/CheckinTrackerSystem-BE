package com.example.ctsbe.dto.group;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GroupAddDTO {
    @NotBlank(message = "Vui lòng nhập tên nhóm!")
    private String groupName;
    private Integer groupLeaderId;
}


