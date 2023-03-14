package com.example.ctsbe.repository;

import com.example.ctsbe.entity.Project;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends JpaRepository<Project,Integer> {
    Page<Project> findByProjectNameContaining(String name, Pageable pageable);
}
