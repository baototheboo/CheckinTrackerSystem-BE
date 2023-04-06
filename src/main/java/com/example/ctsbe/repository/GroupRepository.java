package com.example.ctsbe.repository;

import com.example.ctsbe.entity.Group;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupRepository extends JpaRepository<Group, Integer> {
    Page<Group> findByGroupNameContaining(String name, Pageable pageable);

    @Query(value = "select g from Group g where g.groupLeader.id =:staffId")
    Page<Group> getListGroupByStaffId(int staffId, Pageable pageable);

    @Query(value = "select g from Group g where g.groupLeader.id =:staffId and g.groupName like %:name%")
    Page<Group> getListGroupByStaffIdAndGroupName(int staffId, String name, Pageable pageable);
}
