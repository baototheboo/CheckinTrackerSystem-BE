package com.example.ctsbe.dto.group;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GroupDTO {
    private Integer id;
    private String groupName;
    private String groupLeaderName;
}
