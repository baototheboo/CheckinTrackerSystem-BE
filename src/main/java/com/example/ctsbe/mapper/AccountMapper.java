package com.example.ctsbe.mapper;

import antlr.StringUtils;
import com.example.ctsbe.dto.account.AccountDTO;
import com.example.ctsbe.dto.account.AccountUpdateDTO;
import com.example.ctsbe.dto.account.ProfileDTO;
import com.example.ctsbe.entity.Account;
import com.example.ctsbe.util.DateUtil;
import com.example.ctsbe.util.StringUtil;


public class AccountMapper {

    public static AccountUpdateDTO convertEntityToUpdateDTO(Account account){
        AccountUpdateDTO dto = new AccountUpdateDTO(
                account.getId(),
                account.getPassword()
        );
        return dto;
    }


    public static AccountDTO convertEntityToDTO(Account account){
        StringUtil util = new StringUtil();
        AccountDTO dto = new AccountDTO();
        dto.setId(account.getId());
        dto.setUsername(account.getUsername());
        dto.setEmail(account.getStaff().getEmail());
        dto.setRoleName(util.cutStringRole(account.getRole().getRoleName()));
        dto.setStaffName(account.getStaff().getFirstName() + " " + account.getStaff().getSurname());
        dto.setEnable((account.getEnable() == 1) ? true: false);
        return dto;
    }

    public static ProfileDTO convertAccountToProfile(Account account){
        ProfileDTO dto = new ProfileDTO();
        DateUtil util = new DateUtil();
        dto.setUsername(account.getUsername());
        dto.setFullName(account.getStaff().getSurname() + " " + account.getStaff().getFirstName());
        dto.setEmail(account.getStaff().getEmail());
        dto.setDateOfBirth(util.convertLocalDateToString(account.getStaff().getDateOfBirth()));
        dto.setPhone(account.getStaff().getPhone());
        dto.setRoleName(account.getRole().getRoleName());
        dto.setPromotionLevel(account.getStaff().getPromotionLevel().getId());
        return  dto;
    }



}
