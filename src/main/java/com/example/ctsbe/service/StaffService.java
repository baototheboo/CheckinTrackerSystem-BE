package com.example.ctsbe.service;

import com.example.ctsbe.dto.staff.StaffAddDTO;
import com.example.ctsbe.entity.Staff;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface StaffService {
    Staff addStaff(StaffAddDTO staffAddDTO);

    Page<Staff> getAllStaff(Pageable pageable);

    Page<Staff> getStaffByName(String surname,String firstname, Pageable pageable);

    void changeEnableStaff(int id);

    void changePromotionLevel(int staffId,int levelId);

}
