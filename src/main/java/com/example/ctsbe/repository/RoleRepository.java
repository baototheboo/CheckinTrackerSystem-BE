package com.example.ctsbe.repository;

import com.example.ctsbe.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role,Integer> {
    Role findRoleById(int id);
}
