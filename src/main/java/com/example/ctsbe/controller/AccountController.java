package com.example.ctsbe.controller;


import com.example.ctsbe.dto.account.AccountAddDTO;
import com.example.ctsbe.dto.account.AccountDTO;
import com.example.ctsbe.dto.account.AccountUpdateDTO;
import com.example.ctsbe.entity.Account;
import com.example.ctsbe.mapper.AccountMapper;
import com.example.ctsbe.service.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/accounts")
@CrossOrigin(origins = "*")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @PostMapping("/addAccount")
    public ResponseEntity<?> addAccount(@Valid @RequestBody AccountAddDTO dto){
        try{
            accountService.addAccount(dto);
            return new  ResponseEntity<>("Add account "+dto.getUsername()+" successfully",HttpStatus.OK) ;
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/updateAccount")
    public ResponseEntity<?> updateAccount(@Valid @RequestBody AccountUpdateDTO dto){
        try{
            AccountUpdateDTO updateDTO =  accountService.updateAccount(dto);
            return new ResponseEntity<>("Update account with id "+dto.getId()+" successfully!",HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/changeEnableAccount/{id}")
    public ResponseEntity<?> changeEnableAccount(@PathVariable("id") int id){
        try{
            accountService.changeEnableAccount(id);
            return new ResponseEntity<>("Update enable status successfully",HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping("/getAllAccount")

    public ResponseEntity<Map<String,Object>>  getAllAccount(@RequestParam(defaultValue = "1") int page
            ,@RequestParam(defaultValue = "3") int size
            ,@RequestParam(required = false) String username
            ,@RequestParam(defaultValue = "2") int enable){
        try{
            List<Account> list = new ArrayList<>();
            Pageable pageable =  PageRequest.of(page-1,size);
            Page<Account> accountPage;
            if(username == null){
                if(enable!=1 && enable!=0){
                    accountPage = accountService.getAll(pageable);
                }else {
                    accountPage = accountService.getAccountByEnable((byte) enable,pageable);
                }
            }
            else {
                if(enable == 2){
                    accountPage = accountService.getAccountByUsernameContain(username,pageable);
                }else {
                    accountPage = accountService.getListAccount(username,(byte) enable,pageable);
                }
            }
            list = accountPage.getContent();
            List<AccountDTO> listDto = list.stream().
                    map(AccountMapper::convertEntityToDTO).collect(Collectors.toList());
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


}
