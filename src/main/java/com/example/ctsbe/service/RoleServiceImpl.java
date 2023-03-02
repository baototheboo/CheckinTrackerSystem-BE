package com.example.ctsbe.service;

import com.example.ctsbe.entity.Role;
import com.example.ctsbe.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService{
    @Autowired
    private RoleRepository roleRepository;

    @Override
    public Role findRoleById(int id) {
        return roleRepository.findRoleById(id);
    }
}
