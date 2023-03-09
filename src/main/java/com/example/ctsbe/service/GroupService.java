package com.example.ctsbe.service;

import com.example.ctsbe.dto.group.GroupUpdateDTO;
import com.example.ctsbe.entity.Group;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface GroupService {
    Group addGroup(Group group);

    Page<Group> getAllGroup(Pageable pageable);

    Page<Group> getAllGroupByName(String name,Pageable pageable);
    void editGroup(GroupUpdateDTO dto);

    Group findById(int id);

    void deleteGroup(int id);
}
