package com.example.ctsbe.mapper;

import com.example.ctsbe.dto.staff.StaffAddDTO;
import com.example.ctsbe.dto.staff.StaffDTO;
import com.example.ctsbe.entity.Staff;
import com.example.ctsbe.util.DateUtil;

public class StaffMapper {
    public static StaffDTO convertStaffToStaffDto(Staff staff){
        DateUtil dateUtil = new DateUtil();
        StaffDTO dto = new StaffDTO();
        dto.setId(staff.getId());
        dto.setEmail(staff.getEmail());
        dto.setFullName(staff.getSurname() + " " + staff.getFirstName());
        dto.setDateOfBirth(dateUtil.convertLocalDateToString(staff.getDateOfBirth()));
        dto.setPhone(staff.getPhone());
        dto.setPromotionLevel(staff.getPromotionLevel().getDescription());
        return dto;
    }
}
