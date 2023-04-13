package com.example.ctsbe.service;

import com.example.ctsbe.dto.group.GroupRemoveStaffDTO;
import com.example.ctsbe.dto.group.GroupUpdateDTO;
import com.example.ctsbe.dto.staff.StaffAddDTO;
import com.example.ctsbe.dto.staffProject.StaffProjectAddDTO;
import com.example.ctsbe.entity.Group;
import com.example.ctsbe.entity.Staff;
import javassist.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface GroupService {
    Group addGroup(GroupUpdateDTO dto);

    Page<Group> getAllGroup(Pageable pageable);

    Page<Group> getAllGroupByName(String name,Pageable pageable);
    Page<Group> getListGroupByStaffId(int staffId, Pageable pageable);
    Page<Group> getListGroupByStaffIdAndGroupName(int staffId, String name, Pageable pageable);
    void editGroup(int id,GroupUpdateDTO dto);

    void addStaffToGroup(StaffProjectAddDTO dto);

    void removeStaffFromGroup(GroupRemoveStaffDTO dto);

    Group findById(int id) ;

    void deleteGroup(int id) ;
}
