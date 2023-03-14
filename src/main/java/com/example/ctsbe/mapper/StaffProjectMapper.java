package com.example.ctsbe.mapper;

import com.example.ctsbe.dto.staffProject.StaffInProjectDTO;
import com.example.ctsbe.entity.Staff;
import com.example.ctsbe.entity.StaffProject;

public class StaffProjectMapper {
    public static StaffInProjectDTO convertEntityToDto(StaffProject sp){
        StaffInProjectDTO dto = new StaffInProjectDTO();
        Staff staff = sp.getStaff();
        dto.setFullName(staff.getSurname() + " " + staff.getFirstName());
        dto.setEmail(staff.getEmail());
        dto.setPhone(staff.getPhone());
        return dto;
    }
}
