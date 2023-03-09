package com.example.ctsbe.repository;

import com.example.ctsbe.entity.Group;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupRepository extends JpaRepository<Group,Integer> {
    Page<Group> findByGroupNameContaining(String name, Pageable pageable);
}
