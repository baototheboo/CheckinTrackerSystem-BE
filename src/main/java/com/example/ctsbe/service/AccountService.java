package com.example.ctsbe.service;



import com.example.ctsbe.dto.account.AccountAddDTO;
import com.example.ctsbe.dto.account.AccountUpdateDTO;
import com.example.ctsbe.dto.account.ProfileUpdateDTO;
import com.example.ctsbe.entity.Account;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface AccountService {
    Page<Account> getAll(Pageable pageable);

    Page<Account> getAccountByUsernameContain(String name, Pageable pageable);

    Page<Account> getListAccount(String username,byte enable,Pageable pageable);

    Page<Account> getAccountByEnable(byte enable,Pageable pageable);

    void addAccount(AccountAddDTO accountAddDTO);

    Account getAccountById(int id);

    Account getAccountByUsername(String username);

    void updateAccount(int id, ProfileUpdateDTO dto);

    AccountUpdateDTO resetPassword(int id);

    void changeEnableAccount(int id);

    Optional<Account> findByUsername(String username);
}
