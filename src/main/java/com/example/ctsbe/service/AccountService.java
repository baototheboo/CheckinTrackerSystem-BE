package com.example.ctsbe.service;



import com.example.ctsbe.dto.AccountAddDTO;
import com.example.ctsbe.dto.AccountDTO;
import com.example.ctsbe.dto.AccountUpdateDTO;
import com.example.ctsbe.entity.Account;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface AccountService {
    Page<Account> getAll(Pageable pageable);

    Page<Account> findAccountByUsernameContain(String name, Pageable pageable);

    Page<Account> getListAccount(String username,byte enable,Pageable pageable);

    Page<Account> getAccountByEnable(byte enable,Pageable pageable);

    void addAccount(AccountAddDTO accountAddDTO);

    Account getAccountById(int id);

    AccountUpdateDTO updateAccount(AccountUpdateDTO accountUpdateDTO);

    Optional<Account> findByUsername(String username);
}
