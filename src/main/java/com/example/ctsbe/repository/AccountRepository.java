package com.example.ctsbe.repository;


import com.example.ctsbe.entity.Account;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account,Integer>{
    Optional<Account> findByUsername(String username);

    Page<Account> findByUsernameContaining(String username, Pageable pageable);

    Page<Account> findByUsernameContainingAndVerified(String username,int filter, Pageable pageable);

    Page<Account> findByVerified(int filter,Pageable pageable);


}
