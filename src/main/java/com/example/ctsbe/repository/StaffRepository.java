package com.example.ctsbe.repository;

import com.example.ctsbe.dto.staff.StaffAvailableDTO;
import com.example.ctsbe.entity.Staff;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StaffRepository extends JpaRepository<Staff,Integer> {
    Page<Staff> findBySurnameContainingOrFirstNameContaining(String surname,String firstname, Pageable pageable);

    @Query(value = "select s from Staff s where s.group.id is null")
    List<Staff> get();

    @Query(value = "select a.staff from Account a where a.role.id = 5 and a.staff.group.id is null")
    List<Staff> getListStaffAvailableAddToGroup();

    @Query(value = "SELECT s \n" +
            "FROM Staff s \n" +
            "WHERE s.id NOT IN \n" +
            "    (SELECT sp.staff.id \n" +
            "     FROM StaffProject sp JOIN Project p ON sp.project.id = p.id \n" +
            "     WHERE p.status = 'processing') \n " +
            "AND s.id IN (SELECT a.staff.id FROM Account a WHERE a.enable = 1)")
    List<Staff> getAvailableStaff();

    @Query(value = "select s from Staff s where s.group.id =:groupId")
    Page<Staff> getListStaffByGroup(int groupId,Pageable pageable);

    @Query(value = "select s from Staff s \n" +
            "where s.id in (select a.staff.id from Account a where a.role.id = 4 and a.enable = 1) \n" +
            "and s.id not in (select g.groupLeader.id from Group g)")
    List<Staff> getListGroupLeaderAvailable();
}
