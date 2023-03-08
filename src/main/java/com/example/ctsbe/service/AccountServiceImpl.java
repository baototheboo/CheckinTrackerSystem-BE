package com.example.ctsbe.service;


import com.example.ctsbe.dto.account.AccountAddDTO;
import com.example.ctsbe.dto.account.AccountUpdateDTO;
import com.example.ctsbe.entity.Account;
import com.example.ctsbe.mapper.AccountMapper;
import com.example.ctsbe.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService{

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private RoleService roleService;

    @Autowired
    private StaffService staffService;

    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();


    @Override
    public Page<Account> getAll(Pageable pageable) {
        return accountRepository.findAll(pageable);
    }

    @Override
    public Page<Account> getAccountByUsernameContain(String name, Pageable pageable) {
        return accountRepository.findByUsernameContaining(name,pageable);
    }

    @Override
    public Page<Account> getListAccount(String username, byte enable, Pageable pageable) {
        return accountRepository.findByUsernameContainingAndEnable(username, enable, pageable);
    }

    @Override
    public Page<Account> getAccountByEnable(byte enable, Pageable pageable) {
        return accountRepository.findAccountByEnable(enable, pageable);
    }


    @Override
    public void addAccount(AccountAddDTO dto) {
        Account acc = convertAccountAddDTOToAccount(dto);
        accountRepository.save(acc);
    }

    @Override
    public Account getAccountById(int id) {
        Account account = accountRepository.getById(id);
        return account;
    }

    @Override
    public AccountUpdateDTO updateAccount(AccountUpdateDTO accountUpdateDTO) {
        Account existedAccount = getAccountById(accountUpdateDTO.getId());
        existedAccount.setUsername(accountUpdateDTO.getUsername());
        existedAccount.setPassword(passwordEncoder.encode(accountUpdateDTO.getPassword()));
        existedAccount.setLastUpdated(Instant.now());
        Account updatedAccount = accountRepository.save(existedAccount);
        return AccountMapper.convertEntityToUpdateDTO(updatedAccount);
    }

    @Override
    public void changeEnableAccount(int id) {
        Account account = accountRepository.getById(id);
        if(account.getEnable() == 1){
            account.setEnable((byte)0);
        } else {
            account.setEnable((byte)1);
        }
        accountRepository.save(account);
    }

    @Override
    public Optional<Account> findByUsername(String username) {
        return accountRepository.findByUsername(username);
    }

    private Account convertAccountAddDTOToAccount(AccountAddDTO accountAddDTO) {
        Account account = new Account();
        account.setStaff(staffService.addStaff(accountAddDTO.getStaffAddDTO()));
        account.setUsername(accountAddDTO.getUsername());
        account.setPassword(passwordEncoder.encode(accountAddDTO.getPassword()));
        account.setRole(roleService.findRoleById(accountAddDTO.getRoleId()));
        account.setEnable((byte) 1);
        account.setLastLogin(Instant.now());
        account.setLastUpdated(Instant.now());
        account.setCreatedDate(Instant.now());

        return account;

    }
}
