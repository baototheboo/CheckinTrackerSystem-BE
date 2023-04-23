package com.example.ctsbe.mapper;

import com.example.ctsbe.dto.staffProject.StaffInProjectDTO;
import com.example.ctsbe.entity.Staff;
import com.example.ctsbe.entity.StaffProject;
import com.example.ctsbe.service.AccountService;
import com.example.ctsbe.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class StaffProjectMapper {
    @Autowired
    private static AccountService accountService;
    public StaffProjectMapper(AccountService accountService) {
        this.accountService = accountService;
    }

    public static StaffInProjectDTO convertEntityToDto(StaffProject sp){
        StaffInProjectDTO dto = new StaffInProjectDTO();
        StringUtil util = new StringUtil();
        Staff staff = sp.getStaff();
        dto.setStaffId(staff.getId());
        dto.setUsername(accountService.getAccountById(staff.getId()).getUsername());
        dto.setFullName(staff.getFullName());
        dto.setEmail(staff.getEmail());
        dto.setPhone(staff.getPhone());
        dto.setRoleName(util.cutStringRole(
                accountService.getAccountById(staff.getId()).getRole().getRoleName()));
        dto.setRoleId(
                accountService.getAccountById(staff.getId()).getRole().getId());
        dto.setPromotionLevel(staff.getPromotionLevel().getId());
        return dto;
    }
}
