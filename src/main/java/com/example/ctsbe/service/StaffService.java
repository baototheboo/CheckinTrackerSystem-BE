package com.example.ctsbe.service;

import com.example.ctsbe.dto.group.GroupRemoveStaffDTO;
import com.example.ctsbe.dto.staff.StaffAddDTO;
import com.example.ctsbe.dto.staff.StaffAvailableDTO;
import com.example.ctsbe.dto.staff.StaffUpdateDTO;
import com.example.ctsbe.entity.Account;
import com.example.ctsbe.entity.Staff;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface StaffService {
    Staff addStaff(StaffAddDTO staffAddDTO,int roleId);

    Page<Staff> getAllStaff(Pageable pageable);

    Page<Staff> getStaffByName(String name, Pageable pageable);

    Staff findStaffByEmail(String email);

    void changePromotionLevel(StaffUpdateDTO dto);

    List<Staff> getListAvailableStaff(int groupId,int projectId);

    List<Staff> getStaffsByRole(int role);

    List<Staff> getListStaffAddToGroup();

    Page<Staff> getListStaffByGroup(int groupId,Pageable pageable);

    List<Staff> getListPMAvailable();

    List<Staff> getListGLAvailable();

    Page<Staff> getListStaffByEnable(byte enable,Pageable pageable);

    Page<Staff> getListStaffByNameAndEnable(String name,byte enable,Pageable pageable);

    List<Staff> getListPMInGroup(int groupId);

    void setStaffToPM(int staffId);

    List<Staff> getListStaffForTimeSheet(int staffId,int prjId);

    boolean checkStaffInRemoveFromGroup(GroupRemoveStaffDTO dto);

    Staff getStaffById(int staffId);

    boolean checkStaffInProjectProcessing(Account account);

}
