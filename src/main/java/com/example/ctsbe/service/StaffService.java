package com.example.ctsbe.service;

import com.example.ctsbe.dto.StaffDTO;
import com.example.ctsbe.entity.Staff;

public interface StaffService {
    Staff addStaff(StaffDTO staffDTO);
}
