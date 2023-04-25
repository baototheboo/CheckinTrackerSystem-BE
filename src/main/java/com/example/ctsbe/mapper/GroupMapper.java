package com.example.ctsbe.mapper;

import com.example.ctsbe.dto.group.GroupDTO;
import com.example.ctsbe.dto.group.GroupDetailDTO;
import com.example.ctsbe.entity.Group;

public class GroupMapper {
    public static GroupDTO convertGroupToGroupDTO(Group group){
        GroupDTO dto = new GroupDTO(
                group.getId(),
                group.getGroupName(),
                group.getGroupLeader().getFullName(),
                group.getGroupLeader().getId()
        );
        return  dto;
    }

    /*public static GroupDetailDTO convertGroupToGroupDetailDTO(Group group){
        GroupDetailDTO dto = new GroupDetailDTO();
        dto.setId(group.getId());
        dto.setGroupName(group.getGroupName());
        dto.setGroupLeaderName(group.getGroupLeader().getFullName());
        return dto;
    }*/

}
