package com.example.ctsbe.repository;

import com.example.ctsbe.entity.Project;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Integer> {
    Page<Project> findByProjectNameContaining(String name, Pageable pageable);

    @Query(value = "select p from Project p where p.group.id=:groupId")
    List<Project> getListProjectByGroupId(int groupId);

    Project findProjectByProjectName(String name);

    @Query(value = "select p from Project p where p.projectManager.id=:staffId")
    Page<Project> getListProjectByPMId(int staffId, Pageable pageable);

    @Query(value = "select p from Project p where p.projectManager.id=:staffId and p.projectName like %:name%")
    Page<Project> getListProjectByPMIdAndProjectName(int staffId,String name, Pageable pageable);
}
