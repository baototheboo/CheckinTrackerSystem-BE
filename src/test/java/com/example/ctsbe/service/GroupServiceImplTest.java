package com.example.ctsbe.service;

import com.example.ctsbe.dto.group.GroupDTO;
import com.example.ctsbe.dto.group.GroupRemoveStaffDTO;
import com.example.ctsbe.dto.group.GroupUpdateDTO;
import com.example.ctsbe.dto.staffProject.StaffProjectAddDTO;
import com.example.ctsbe.entity.Group;
import com.example.ctsbe.mapper.GroupMapper;
import com.example.ctsbe.repository.GroupRepository;
import com.example.ctsbe.repository.StaffRepository;
import com.example.ctsbe.util.DateUtil;
import javassist.NotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.OngoingStubbing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class GroupServiceImplTest {
    @Autowired
    private GroupService groupService;
    @Autowired
    private GroupRepository groupRepository;
    @Autowired
    private StaffRepository staffRepository;
    Pageable pageable = PageRequest.of(0, 10);

    DateUtil dateUtil = new DateUtil();
    /*@Test
    void addGroup() {
        GroupUpdateDTO dto = new GroupUpdateDTO("test 3 group",39);
        Group actualRes = groupService.addGroup(dto);
        Group expectedRes = groupRepository.findGroupByGroupName("test 3 group");
        assertEquals(expectedRes,actualRes);
    }

    @Test
    void getAllGroup() {
        Page<Group> groupPage = groupService.getAllGroup(pageable);
        List<Group> actualRes = groupPage.getContent();
        assertEquals(10,actualRes.size());
    }

    @Test
    void getAllGroupByName() {
        Page<Group> groupPage = groupService.getAllGroupByName("test 3 group",pageable);
        List<Group> actualRes = groupPage.getContent();
        assertEquals(0,actualRes.size());
    }

    @Test
    void getListGroupByStaffId() {
        Page<Group> groupPage = groupService.getListGroupByStaffId(20,pageable);
        List<Group> actualRes = groupPage.getContent();
        assertEquals(0,actualRes.size());
    }

    @Test
    void getListGroupByStaffIdAndGroupName() {
        Page<Group> groupPage = groupService.getListGroupByStaffIdAndGroupName(30,"php",pageable);
        List<Group> actualRes = groupPage.getContent();
        assertEquals(1,actualRes.size());
    }

    @Test
    void editGroup() {
        GroupUpdateDTO dto = new GroupUpdateDTO("Game Group",2);
        groupService.editGroup(2,dto);
        Group getByName = groupRepository.findGroupByGroupName("Game Group");
        Group getById = groupService.findById(2);
        assertEquals(getById,getByName);
    }

    @Test
    void addStaffToGroup() {
        StaffProjectAddDTO dto = new StaffProjectAddDTO();
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(6);
        int groupId = 10;
        dto.setStaffId(list);
        dto.setProjectId(groupId);
        groupService.addStaffToGroup(dto);
        Group actualRes1 = staffRepository.findStaffById(1).getGroup();
        Group actualRes2 = staffRepository.findStaffById(6).getGroup();
        Group expectRes = groupRepository.getById(groupId);
        assertEquals(actualRes1,expectRes);
    }

    @Test
    void removeStaffFromGroup() {
        GroupRemoveStaffDTO dto = new GroupRemoveStaffDTO();
        List<Integer> list = new ArrayList<>();
        list.add(4);
        dto.setStaffId(list);
        Group a = staffRepository.findStaffById(4).getGroup();
        groupService.removeStaffFromGroup(dto);
        Group actualRes = staffRepository.findStaffById(4).getGroup();
        assertEquals(null,actualRes);

    }

    @Test
    void findById() {
        Group group = groupService.findById(2);
        GroupDTO actualRes = GroupMapper.convertGroupToGroupDTO(group);
        GroupDTO expectRes = new GroupDTO(2,"Game Group","le hung",2);
        assertEquals(expectRes,actualRes);

    }

    @Test
    void deleteGroup() throws NotFoundException {
        groupService.deleteGroup(2);
        Group group = groupService.findById(2);
//        assertEquals(null,group);
    }*/
}