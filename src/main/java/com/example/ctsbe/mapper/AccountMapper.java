package com.example.ctsbe.mapper;

import com.example.ctsbe.dto.AccountDTO;
import com.example.ctsbe.dto.AccountUpdateDTO;
import com.example.ctsbe.entity.Account;


public class AccountMapper {

    public static AccountUpdateDTO convertEntityToUpdateDTO(Account account){
        AccountUpdateDTO dto = new AccountUpdateDTO(
                account.getId(),
                account.getUsername(),
                account.getPassword()
        );
        return dto;
    }


    public static AccountDTO convertEntityToDTO(Account account){
        AccountDTO dto = new AccountDTO();
        dto.setId(account.getId());
        dto.setUsername(account.getUsername());
        dto.setEmail(account.getStaff().getEmail());
        dto.setRoleName(account.getRole().getRoleName());
        dto.setStaffName(account.getStaff().getFirstName() + " " + account.getStaff().getSurname());
        dto.setIsEnable((account.getEnable() == 1) ? "Enable" : "Disable");
        return dto;
    }



}
