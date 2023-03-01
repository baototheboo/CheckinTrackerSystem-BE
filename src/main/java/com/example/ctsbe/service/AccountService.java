package com.example.ctsbe.service;



import com.example.ctsbe.entity.Account;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface AccountService {
    Page<Account> getAll(Pageable pageable);

    Page<Account> findAccountByUsernameContain(String name, Pageable pageable);

    void addAccount(Account account);

    Optional<Account> findByUsername(String username);
}
