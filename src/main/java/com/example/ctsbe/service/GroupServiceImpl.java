package com.example.ctsbe.service;

import com.example.ctsbe.dto.group.GroupAddDTO;
import com.example.ctsbe.dto.group.GroupDTO;
import com.example.ctsbe.dto.group.GroupRemoveStaffDTO;
import com.example.ctsbe.dto.group.GroupUpdateDTO;
import com.example.ctsbe.dto.staffProject.StaffProjectAddDTO;
import com.example.ctsbe.entity.Account;
import com.example.ctsbe.entity.Group;
import com.example.ctsbe.entity.Staff;
import com.example.ctsbe.repository.AccountRepository;
import com.example.ctsbe.repository.GroupRepository;
import com.example.ctsbe.repository.RoleRepository;
import com.example.ctsbe.repository.StaffRepository;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
public class GroupServiceImpl implements GroupService {

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private StaffRepository staffRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public Group addGroup(GroupAddDTO dto) {
        Group addGroup = new Group();
        addGroup.setGroupName(dto.getGroupName());
        addGroup.setGroupLeader(staffRepository.getById(dto.getGroupLeaderId()));
        addGroup.setCreatedDate(Instant.now());
        addGroup.setLastUpdated(Instant.now());
        return groupRepository.save(addGroup);
    }

    @Override
    public Page<Group> getAllGroup(Pageable pageable) {
        return groupRepository.findAll(pageable);
    }

    @Override
    public Page<Group> getAllGroupByName(String name, Pageable pageable) {
        return groupRepository.findByGroupNameContaining(name, pageable);
    }

    @Override
    public Page<Group> getListGroupByStaffId(int staffId, Pageable pageable) {
        return groupRepository.getListGroupByStaffId(staffId, pageable);
    }

    @Override
    public Page<Group> getListGroupByStaffIdAndGroupName(int staffId, String name, Pageable pageable) {
        return groupRepository.getListGroupByStaffIdAndGroupName(staffId, name, pageable);
    }


    @Override
    public void editGroup(int id, GroupUpdateDTO dto) {
        Group existedGroup = groupRepository.getById(id);
        Account oldGLAcc = accountRepository.getById(existedGroup.getGroupLeader().getId());
        Account newGLAcc = accountRepository.getById(dto.getGroupLeaderId());
        //Staff newGL = staffRepository.findStaffById(dto.getGroupLeaderId());
        existedGroup.setGroupName(dto.getGroupName());
        if(dto.getGroupLeaderId() != existedGroup.getGroupLeader().getId()){
            existedGroup.setGroupLeader(newGLAcc.getStaff());
            existedGroup.setLastUpdated(Instant.now());
            oldGLAcc.setRole(roleRepository.getById(3));
            oldGLAcc.setLastUpdated(Instant.now());
            newGLAcc.setRole(roleRepository.getById(4));
            newGLAcc.setLastUpdated(Instant.now());
            accountRepository.save(oldGLAcc);
            accountRepository.save(newGLAcc);
        }
        groupRepository.save(existedGroup);
    }

    @Override
    public void addStaffToGroup(StaffProjectAddDTO dto) {
        List<Integer> listStaffId = dto.getStaffId();
        for (int i = 0; i < listStaffId.size(); i++) {
            Staff existedStaff = staffRepository.getById(listStaffId.get(i));
            existedStaff.setGroup(groupRepository.getById(dto.getProjectId()));
            staffRepository.save(existedStaff);
        }
    }

    @Override
    public void removeStaffFromGroup(GroupRemoveStaffDTO dto) {
        List<Integer> listStaffId = dto.getStaffId();
        for (int i = 0; i < listStaffId.size(); i++) {
            Staff existedStaff = staffRepository.getById(listStaffId.get(i));
            existedStaff.setGroup(null);
            staffRepository.save(existedStaff);
        }
    }

    @Override
    public Group findById(int id) {
        Group existedGroup = groupRepository.getById(id);
        if(existedGroup!=null) return existedGroup;
        else return null;
    }

    @Override
    public void deleteGroup(int id) {
        Group existedGroup = this.findById(id);
        groupRepository.delete(existedGroup);
    }

    @Override
    public void addGLToGroup(int glId, int groupId) {
        Staff existedStaff = staffRepository.getById(glId);
        existedStaff.setGroup(groupRepository.getById(groupId));
        staffRepository.save(existedStaff);
    }
}
