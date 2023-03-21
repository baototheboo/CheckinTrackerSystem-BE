package com.example.ctsbe.service;

import com.example.ctsbe.dto.staff.StaffAddDTO;
import com.example.ctsbe.dto.staff.StaffAvailableDTO;
import com.example.ctsbe.entity.Staff;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface StaffService {
    Staff addStaff(StaffAddDTO staffAddDTO);

    Page<Staff> getAllStaff(Pageable pageable);

    Page<Staff> getStaffByName(String surname,String firstname, Pageable pageable);


    void changePromotionLevel(int staffId,int levelId);

    List<Staff> getListAvailableStaff();

    List<Staff> getStaffsByRole(int role);

    Page<Staff> getListStaffByGroup(int groupId,Pageable pageable);

    List<Staff> getListPMAvailable(int role);

    List<Staff> getListGLAvailable();

}
