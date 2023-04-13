package com.example.ctsbe.service;

import com.example.ctsbe.dto.account.AccountAddDTO;
import com.example.ctsbe.dto.account.AccountDTO;
import com.example.ctsbe.dto.staff.StaffAddDTO;
import com.example.ctsbe.entity.Account;
import com.example.ctsbe.entity.Group;
import com.example.ctsbe.mapper.AccountMapper;
import com.example.ctsbe.repository.AccountRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class AccountServiceImplTest {
    @Autowired
    private AccountService accountService;
    @Autowired
    private AccountRepository accountRepository;
    Pageable pageable = PageRequest.of(0, 10);

    /*@Test
    void getAll() {
        Page<Account> accountPage = accountRepository.findAll(pageable);
        List<Account> actualRes = accountPage.getContent();
        assertEquals(39,actualRes.size());
    }

    @Test
    void getAccountByUsernameContain() {
        Page<Account> accountPage = accountRepository.findByUsernameContaining("l",pageable);
        List<Account> actualRes = accountPage.getContent();
        assertEquals(9,actualRes.size());
    }

    @Test
    void getListAccount() {
        Page<Account> accountPage = accountRepository.findByUsernameContainingAndEnable("l",(byte)1,pageable);
        List<Account> actualRes = accountPage.getContent();
        assertEquals(4,actualRes.size());
    }

    @Test
    void getAccountByEnable() {
        Page<Account> accountPage = accountRepository.findAccountByEnable((byte)1,pageable);
        List<Account> actualRes = accountPage.getContent();
        assertEquals(24,actualRes.size());
    }

    @Test
    void addAccount() {
        StaffAddDTO staffAddDTO = new StaffAddDTO();
        staffAddDTO.setEmail("lmh@gmail.com");
        staffAddDTO.setFirstName("Hung");
        staffAddDTO.setSurname("Be Hoai");
        staffAddDTO.setDateOfBirth("2001-05-19");
        staffAddDTO.setPhone("0376771730");
        AccountAddDTO accountAddDTO = new AccountAddDTO();
        accountAddDTO.setUsername("bmh");
        accountAddDTO.setPassword("123456");
        accountAddDTO.setRoleId(5);
        accountAddDTO.setStaffAddDTO(staffAddDTO);
        Account actualRes = accountService.addAccount(accountAddDTO);
        Account expectedRes = accountRepository.findByUsername("bmh").get();
        assertEquals(expectedRes,actualRes);
    }

    @Test
    void getAccountById() {
        Account acc = accountService.getAccountById(5);
        AccountDTO actualRes = AccountMapper.convertEntityToDTO(acc);
        AccountDTO expectRes = new AccountDTO(5,"bhl","linh123@gmail.com","Be Hoai Linh","HUMAN RESOURCE",null,null,true);
        assertEquals(expectRes,actualRes);
        
    }

    @Test
    void getAccountByUsername() {
        Account acc = accountService.getAccountByUsername("bhl");
        AccountDTO actualRes = AccountMapper.convertEntityToDTO(acc);
        AccountDTO expectRes = new AccountDTO(5,"bhl","linh123@gmail.com","Be Hoai Linh","HUMAN RESOURCE",null,null,true);
        assertEquals(expectRes,actualRes);
    }

    @Test
    void getAccountByEmail() {
        Account acc = accountService.getAccountByEmail("linh123@gmail.com");
        AccountDTO actualRes = AccountMapper.convertEntityToDTO(acc);
        AccountDTO expectRes = new AccountDTO(5,"bhl","linh123@gmail.com","Be Hoai Linh","HUMAN RESOURCE",null,null,true);
        assertEquals(expectRes,actualRes);
    }

    @Test
    void updateAccount() {
    }

    @Test
    void resetPassword() {
    }

    @Test
    void changeEnableAccount() {
    }

    @Test
    void findByUsername() {
    }*/
}