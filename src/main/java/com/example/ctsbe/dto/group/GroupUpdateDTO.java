package com.example.ctsbe.dto.group;

import lombok.Data;

@Data
public class GroupUpdateDTO {
    private Integer id;
    private String groupName;
    private Integer groupLeaderId;
}
