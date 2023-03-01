package com.example.ctsbe.service;


import com.example.ctsbe.entity.Account;
import com.example.ctsbe.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService{

    @Autowired
    private AccountRepository accountRepository;


    @Override
    public Page<Account> getAll(Pageable pageable) {
        return accountRepository.findAll(pageable);
    }

    @Override
    public Page<Account> findAccountByUsernameContain(String name, Pageable pageable) {
        return accountRepository.findByUsernameContaining(name,pageable);
    }

    @Override
    public void addAccount(Account account) {

    }

    @Override
    public Optional<Account> findByUsername(String username) {
        return accountRepository.findByUsername(username);
    }
}
