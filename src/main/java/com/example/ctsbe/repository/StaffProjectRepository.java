package com.example.ctsbe.repository;

import com.example.ctsbe.entity.Project;
import com.example.ctsbe.entity.StaffProject;
import com.example.ctsbe.entity.StaffProjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StaffProjectRepository extends JpaRepository<StaffProject, StaffProjectId> {
    Page<StaffProject> findByProject(Project project, Pageable pageable);
}
