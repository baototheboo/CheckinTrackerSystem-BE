package com.example.ctsbe.service;


import com.example.ctsbe.constant.ApplicationConstant;
import com.example.ctsbe.dto.account.AccountAddDTO;
import com.example.ctsbe.dto.account.AccountUpdateDTO;
import com.example.ctsbe.dto.account.ProfileUpdateDTO;
import com.example.ctsbe.dto.account.ResetPasswordAdminDTO;
import com.example.ctsbe.entity.Account;
import com.example.ctsbe.mapper.AccountMapper;
import com.example.ctsbe.repository.AccountRepository;
import com.example.ctsbe.util.DateUtil;
import com.example.ctsbe.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private RoleService roleService;

    @Autowired
    private StaffService staffService;
    @Autowired
    private EmailService emailService;
    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    DateUtil dateUtil = new DateUtil();


    @Override
    public Page<Account> getAll(Pageable pageable) {
        return accountRepository.findAll(pageable);
    }

    @Override
    public Page<Account> getAccountByUsernameContain(String name, Pageable pageable) {
        return accountRepository.findByUsernameContaining(name, pageable);
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
    public Account addAccount(AccountAddDTO dto) {
        Account acc = convertAccountAddDTOToAccount(dto);
        return accountRepository.save(acc);
    }

    @Override
    public Account getAccountById(int id) {
        Account account = accountRepository.getById(id);
        return account;
    }

    @Override
    public Account getAccountByUsername(String username) {
        Optional<Account> account = accountRepository.findByUsername(username);
        if(account.isPresent()){
            Account acc = account.get();
            return acc;
        }else{
            return null;
        }
    }

    @Override
    public Account getAccountByEmail(String email) {
        return accountRepository.getAccByEmail(email);
    }

    @Override
    public void updateAccount(int id, ProfileUpdateDTO dto) {
        Account account = accountRepository.getById(id);
        DateUtil util = new DateUtil();
        //account.setUsername(dto.getUsername());
        account.getStaff().setSurname(dto.getSurname());
        account.getStaff().setFirstName(dto.getFirstName());
        //account.getStaff().setEmail(dto.getEmail());
        account.getStaff().setDateOfBirth(util.convertStringToLocalDate(dto.getDateOfBirth()));
        account.getStaff().setPhone(dto.getPhone());
        account.setLastUpdated(Instant.now());
        account.getStaff().setLastUpdated(Instant.now());
        accountRepository.save(account);
    }

    @Override
    public void resetPassword(Account account,String newPassword) {
        account.setPassword(passwordEncoder.encode(newPassword));
        account.setLastUpdated(Instant.now());
        accountRepository.save(account);
    }

    @Override
    public void changeEnableAccount(Account account) {
        if (account.getEnable() == 1) {
            account.setEnable((byte) 0);
        } else {
            account.setEnable((byte) 1);
        }
        account.setLastUpdated(Instant.now());
        accountRepository.save(account);
    }

    @Override
    public void resetPasswordForAdmin(String username, ResetPasswordAdminDTO dto) {
        Account existedAccount = getAccountByUsername(username);
        existedAccount.setPassword(passwordEncoder.encode(dto.getNewPassword()));
        existedAccount.setLastUpdated(Instant.now());
        accountRepository.save(existedAccount);
    }

    @Override
    public Optional<Account> findByUsername(String username) {
        return accountRepository.findByUsername(username);
    }

    private Account convertAccountAddDTOToAccount(AccountAddDTO accountAddDTO) {
        Account account = new Account();
        account.setUsername(accountAddDTO.getUsername());
        account.setPassword(passwordEncoder.encode(accountAddDTO.getPassword()));
        account.setRole(roleService.findRoleById(accountAddDTO.getRoleId()));
        account.setEnable((byte) 1);
        account.setLastLogin(Instant.now());
        account.setLastUpdated(Instant.now());
        account.setCreatedDate(Instant.now());
        account.setStaff(staffService.addStaff(accountAddDTO.getStaffAddDTO()));
        return account;
    }
}
