package com.example.ctsbe.service;

import com.example.ctsbe.dto.staff.StaffAddDTO;
import com.example.ctsbe.dto.staff.StaffAvailableDTO;
import com.example.ctsbe.dto.staff.StaffUpdateDTO;
import com.example.ctsbe.entity.Staff;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface StaffService {
    Staff addStaff(StaffAddDTO staffAddDTO);

    Page<Staff> getAllStaff(Pageable pageable);

    Page<Staff> getStaffByName(String surname,String firstname, Pageable pageable);

    Staff findStaffByEmail(String email);

    void changePromotionLevel(StaffUpdateDTO dto);

    List<Staff> getListAvailableStaff(int groupId);

    List<Staff> getStaffsByRole(int role);

    List<Staff> getListStaffAddToGroup();

    Page<Staff> getListStaffByGroup(int groupId,Pageable pageable);

    List<Staff> getListPMAvailable();

    List<Staff> getListGLAvailable();

}
