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
    @Query(value = "select p from Project p where p.projectName=:name order by p.status desc ")
    Page<Project> getListProjectByName(String name, Pageable pageable);

    @Query(value = "select p from Project p where p.group.id=:groupId order by p.status desc ")
    List<Project> getListProjectByGroupId(int groupId);

    @Query(value = "select p from Project p order by p.status desc ")
    Page<Project> getAllProject(Pageable pageable);

    Project findProjectByProjectName(String name);

    @Query(value = "select p from Project p where p.projectManager.id=:staffId order by p.status desc")
    Page<Project> getListProjectByPMId(int staffId, Pageable pageable);

    @Query(value = "select p from Project p where p.projectManager.id=:staffId and p.projectName like %:name% order by p.status desc")
    Page<Project> getListProjectByPMIdAndProjectName(int staffId,String name, Pageable pageable);
}
