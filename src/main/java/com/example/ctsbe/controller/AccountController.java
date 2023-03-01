package com.example.ctsbe.controller;


import com.example.ctsbe.dto.AccountDTO;
import com.example.ctsbe.dto.ResponseModel;
import com.example.ctsbe.entity.Account;
import com.example.ctsbe.service.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/accounts")
public class AccountController {

    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private AccountService accountService;

    ResponseModel responseModel = new ResponseModel();

    @GetMapping("/getAllAccount")
    public ResponseEntity<Map<String,Object>>  getAllAccount(@RequestParam(defaultValue = "1") int page
            ,@RequestParam(defaultValue = "3") int size
            ,@RequestParam(required = false) String username ){
        try{
            List<Account> list = new ArrayList<>();
            Pageable pageable =  PageRequest.of(page-1,size);
            Page<Account> accountPage;
            if(username == null){
                accountPage = accountService.getAll(pageable);
            } else {
                accountPage = accountService.findAccountByUsernameContain(username,pageable);
            }
            list = accountPage.getContent();
            List<AccountDTO> listDto = list.stream().
                    map(this::convertEntityToDTO).collect(Collectors.toList());
            Map<String,Object> response = new HashMap<>();
            response.put("list",listDto);
            response.put("currentPage",accountPage.getNumber());
            response.put("allProducts",accountPage.getTotalElements());
            response.put("allPages",accountPage.getTotalPages());
            return new ResponseEntity<>(response,HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/addAccount")
    public void addAccount(){

    }

    private AccountDTO convertEntityToDTO(Account account){
        AccountDTO dto = new AccountDTO();
        dto.setUsername(account.getUsername());
        dto.setRoleName(account.getRole().getRoleName());
        dto.setStaffName(account.getStaff().getFirstName() + " " + account.getStaff().getSurname());
        dto.setLastLogin(account.getLastLogin());
        dto.setEnable(account.getStaff().getEnable());
        return dto;
    }
}
