package com.example.ctsbe.dto.group;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GroupAddDTO {
    private String groupName;
    private Integer groupLeaderId;
}


