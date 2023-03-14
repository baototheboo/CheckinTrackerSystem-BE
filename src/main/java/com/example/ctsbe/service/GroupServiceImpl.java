package com.example.ctsbe.service;

import com.example.ctsbe.dto.group.GroupUpdateDTO;
import com.example.ctsbe.entity.Group;
import com.example.ctsbe.repository.GroupRepository;
import javassist.NotFoundException;
import org.springframework.beans.NotReadablePropertyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.time.Instant;
import java.util.List;

@Service
public class GroupServiceImpl implements GroupService {

    @Autowired
    private GroupRepository groupRepository;

    @Override
    public Group addGroup(Group group) {
        Group addGroup = new Group();
        addGroup.setGroupName(group.getGroupName());
        addGroup.setGroupLeaderId(group.getGroupLeaderId());
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
    public void editGroup(GroupUpdateDTO dto) {
        Group existedGroup = groupRepository.getById(dto.getId());
        existedGroup.setGroupName(dto.getGroupName());
        existedGroup.setGroupLeaderId(dto.getGroupLeaderId());
        existedGroup.setLastUpdated(Instant.now());
        groupRepository.save(existedGroup);
    }

    @Override
    public Group findById(int id) throws NotFoundException {
        Group existedGroup = groupRepository.getById(id);
        if(existedGroup.getId() == null){
            throw new NotFoundException("Cannot found this group.");
        }
        else return existedGroup ;
    }

    @Override
    public void deleteGroup(int id) throws NotFoundException {
        Group existedGroup = this.findById(id);
        groupRepository.delete(existedGroup);
    }
}
