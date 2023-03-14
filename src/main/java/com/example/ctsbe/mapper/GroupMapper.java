package com.example.ctsbe.mapper;

import com.example.ctsbe.dto.group.GroupDTO;
import com.example.ctsbe.entity.Group;

public class GroupMapper {
    public static GroupDTO convertGroupToGroupDTO(Group group){
        GroupDTO dto = new GroupDTO(
                group.getId(),
                group.getGroupName(),
                group.getGroupLeader().getSurname() + " "
                        + group.getGroupLeader().getFirstName()
        );
        return  dto;
    }
}
