package com.example.ctsbe.repository;

import com.example.ctsbe.entity.Project;
import com.example.ctsbe.entity.Staff;
import com.example.ctsbe.entity.StaffProject;
import com.example.ctsbe.entity.StaffProjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StaffProjectRepository extends JpaRepository<StaffProject, StaffProjectId> {
    Page<StaffProject> findByProject(Project project, Pageable pageable);

    @Query(value = "Select sp.project from StaffProject sp where sp.staff.id =:staffId")
    List<Project> getListProjectByStaffId(int staffId);

    @Query(value = "Select sp.staff from StaffProject sp where sp.project.id =:projectId")
    List<Staff> getListStaffByProjectId(int projectId);

}
