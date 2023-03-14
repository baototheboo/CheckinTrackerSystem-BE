package com.example.ctsbe.service;

import com.example.ctsbe.dto.group.GroupUpdateDTO;
import com.example.ctsbe.entity.Group;
import javassist.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface GroupService {
    Group addGroup(GroupUpdateDTO dto);

    Page<Group> getAllGroup(Pageable pageable);

    Page<Group> getAllGroupByName(String name,Pageable pageable);
    void editGroup(int id,GroupUpdateDTO dto);

    Group findById(int id) throws NotFoundException;

    void deleteGroup(int id) throws NotFoundException;
}
