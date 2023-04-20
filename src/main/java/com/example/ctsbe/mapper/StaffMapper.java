package com.example.ctsbe.mapper;

import com.example.ctsbe.dto.staff.StaffAddDTO;
import com.example.ctsbe.dto.staff.StaffAvailableDTO;
import com.example.ctsbe.dto.staff.StaffDTO;
import com.example.ctsbe.entity.Staff;
import com.example.ctsbe.service.AccountService;
import com.example.ctsbe.util.DateUtil;
import com.example.ctsbe.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class StaffMapper {
    @Autowired
    private static AccountService accountService;

    public StaffMapper(AccountService accountService) {
        this.accountService = accountService;
    }

    public static StaffDTO convertStaffToStaffDto(Staff staff){
        DateUtil dateUtil = new DateUtil();
        StringUtil stringUtil = new StringUtil();
        StaffDTO dto = new StaffDTO();
        dto.setId(staff.getId());
        dto.setUsername(accountService.getAccountById(staff.getId()).getUsername());
        dto.setEmail(staff.getEmail());
        dto.setFullName(staff.getFullName());
        dto.setDateOfBirth(dateUtil.convertLocalDateToString(staff.getDateOfBirth()));
        dto.setPhone(staff.getPhone());
        dto.setPromotionLevel(staff.getPromotionLevel().getName());
        dto.setPromotionLevelId(staff.getPromotionLevel().getId());
        dto.setRoleName(stringUtil.cutStringRole(
                accountService.getAccountById(staff.getId()).getRole().getRoleName()));
        dto.setEnable((accountService.getAccountById(staff.getId()).getEnable() == 1) ? true : false);
        return dto;
    }

    public static StaffAvailableDTO convertStaffToStaffAvailableDto(Staff staff){
        StaffAvailableDTO dto = new StaffAvailableDTO();
        dto.setId(staff.getId());
        dto.setFullName(staff.getFullName());
        return dto;
    }
}
