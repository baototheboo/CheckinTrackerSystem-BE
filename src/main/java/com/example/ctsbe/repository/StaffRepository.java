package com.example.ctsbe.repository;

import com.example.ctsbe.entity.Staff;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StaffRepository extends JpaRepository<Staff,Integer> {
    Page<Staff> findBySurnameContainingOrFirstNameContaining(String surname,String firstname, Pageable pageable);
}
