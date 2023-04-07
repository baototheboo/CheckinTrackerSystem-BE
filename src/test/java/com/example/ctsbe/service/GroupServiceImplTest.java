package com.example.ctsbe.service;

import com.example.ctsbe.entity.Group;
import com.example.ctsbe.repository.GroupRepository;
import javassist.NotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.OngoingStubbing;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
class GroupServiceImplTest {
    @InjectMocks
    GroupServiceImpl groupService;
    @Mock
    GroupRepository groupRepository;
    @Test
    void addGroup() {
    }

    @Test
    void getAllGroup() {

    }

    @Test
    void getAllGroupByName() {
    }

    @Test
    void getListGroupByStaffId() {
    }

    @Test
    void getListGroupByStaffIdAndGroupName() {
    }

    @Test
    void editGroup() {
    }

    @Test
    void addStaffToGroup() {
    }

    @Test
    void removeStaffFromGroup() {
    }

    @Test
    void findById() throws NotFoundException {
        Group group = new Group();
        Mockito.when(groupRepository.getById(2)).thenReturn(group);
        Group expectGroup = groupService.findById(2);
        assertEquals(expectGroup,group);
    }

    @Test
    void deleteGroup() {
    }
}